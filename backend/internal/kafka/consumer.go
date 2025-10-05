package kafka

import (
	k "backend/internal/kafka/handlers"
	"backend/internal/repository"
	"fmt"
	"log/slog"
	"time"

	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

type Handler interface {
	HandleMessage(msg []byte) error
}

type Consumer struct {
	consumer *kafka.Consumer
	repo     *repository.Repository
	isStoped bool
	log      *slog.Logger
}

func NewConsumer(address string, repo *repository.Repository, topics []string, groupId string, logger *slog.Logger) (*Consumer, error) {
	cfg := kafka.ConfigMap{
		"bootstrap.servers":        address,
		"session.timeout.ms":       30000,
		"group.id":                 groupId,
		"enable.auto.commit":       true,
		"enable.auto.offset.store": false,
		"auto.offset.reset":        "earliest",
	}

	var consumer *kafka.Consumer
	var err error

	// костыльный ретрай
	for i := 0; i < 5; i++ {
		consumer, err = kafka.NewConsumer(&cfg)
		if err == nil {
			break
		}
		logger.Error("consumer creation failed, recreation in 4 sec", "attempt", i+1, "error", err)
		time.Sleep(4 * time.Second)
	}
	if err != nil {
		return nil, fmt.Errorf("failed to create consumer after 5 attempts: %w", err)
	}

	_, err = consumer.GetMetadata(nil, true, 10000) // 10 сек
	if err != nil {
		logger.Error("pinging failed")
	}

	if err := consumer.SubscribeTopics(topics, nil); err != nil {
		consumer.Close()
		return nil, fmt.Errorf("failed to subscribe to topics: %w", err)
	}

	logger.Info("successfully connected to kafka and subscribed to topics", "topics", topics)

	return &Consumer{consumer: consumer, repo: repo, isStoped: false, log: logger}, nil
}

func (c *Consumer) Start() {
	c.log.Info("starting consumer")

	for {
		if c.isStoped {
			return
		}

		msg, err := c.consumer.ReadMessage(-1)
		if err != nil {
			c.log.Error("reading kafka message error", "error", err)
			continue
		}

		var funcToHandleMsg Handler
		switch *msg.TopicPartition.Topic {
		case "app_topic":
			funcToHandleMsg = k.NewAppMsgHandler(c.repo, c.log)
		case "category_topic":
			c.log.Warn("no handler for topic, skipping", "topic", "category_topic")
			continue
		case "screenshot_topic":
			c.log.Warn("no handler for topic, skipping", "topic", "screenshot_topic")
			continue
		default:
			c.log.Error("unexpected topic", "topic", *msg.TopicPartition.Topic)
			continue
		}

		err = funcToHandleMsg.HandleMessage(msg.Value)
		if err != nil {
			c.log.Error("handling message error", "error", err)
			continue
		}

		c.log.Info("message handled successfully",
			"topic", *msg.TopicPartition.Topic,
			"partition", msg.TopicPartition.Partition,
			"offset", msg.TopicPartition.Offset)

		if _, err = c.consumer.StoreMessage(msg); err != nil {
			c.log.Error("kafka store offset failed", "error", err)
		}
	}
}

func (c *Consumer) Stop() error {
	c.log.Info("stopping consumer")
	c.isStoped = true

	if _, err := c.consumer.Commit(); err != nil {
		c.log.Error("error committing while stopping", "error", err)
	}

	if err := c.consumer.Close(); err != nil {
		return fmt.Errorf("error closing consumer: %w", err)
	}

	c.log.Info("consumer stopped successfully")
	return nil
}
