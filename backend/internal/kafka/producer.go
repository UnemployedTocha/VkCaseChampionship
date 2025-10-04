package kafka

import (
	"backend/internal/models"
	"encoding/json"
	"errors"
	"fmt"
	"log/slog"

	"github.com/confluentinc/confluent-kafka-go/v2/kafka"
)

var UnknownEventError = errors.New("unknown event type")

type Producer struct {
	producer *kafka.Producer
	log      *slog.Logger
}

func NewProducer(address string, logger *slog.Logger) (*Producer, error) {
	cfg := kafka.ConfigMap{
		"bootstrap.servers": address,
	}

	logger.Info("making consumer")

	p, _ := kafka.NewProducer(&cfg)

	return &Producer{producer: p, log: logger}, nil
}

func (p *Producer) ProduceApp(app models.App, topic string) error {
	msg, err := json.Marshal(app)
	if err != nil {
		return fmt.Errorf("order json decoding error: %w", err)
	}

	kafkaChan := make(chan kafka.Event)
	err = p.producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{Topic: &topic, Partition: kafka.PartitionAny},
		Value:          msg,
	}, kafkaChan)

	if err != nil {
		return fmt.Errorf("kafka produce error: %w", err)
	}

	event := <-kafkaChan
	switch er := event.(type) {
	case *kafka.Message:
		{
			fmt.Println("msg successfully sent!!!1!")
			return nil
		}
	case *kafka.Error:
		return fmt.Errorf("kafka response error: %w", er)
	default:
		return UnknownEventError
	}
}

func (p *Producer) ProduceCategory(ctgry models.Category, topic string) error {
	msg, err := json.Marshal(ctgry)
	if err != nil {
		return fmt.Errorf("order json decoding error: %w", err)
	}

	kafkaChan := make(chan kafka.Event)
	err = p.producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{Topic: &topic, Partition: kafka.PartitionAny},
		Value:          msg,
	}, kafkaChan)

	if err != nil {
		return fmt.Errorf("kafka produce error: %w", err)
	}

	event := <-kafkaChan
	switch er := event.(type) {
	case *kafka.Message:
		{
			fmt.Println("msg successfully sent!!!1!")
			return nil
		}
	case *kafka.Error:
		return fmt.Errorf("kafka response error: %w", er)
	default:
		return UnknownEventError
	}
}

func (p *Producer) ProduceScreenshot(scrnsht models.Screenshot, topic string) error {
	msg, err := json.Marshal(scrnsht)
	if err != nil {
		return fmt.Errorf("order json decoding error: %w", err)
	}

	kafkaChan := make(chan kafka.Event)
	err = p.producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{Topic: &topic, Partition: kafka.PartitionAny},
		Value:          msg,
	}, kafkaChan)

	if err != nil {
		return fmt.Errorf("kafka produce error: %w", err)
	}

	event := <-kafkaChan
	switch er := event.(type) {
	case *kafka.Message:
		{
			fmt.Println("msg successfully sent!!!1!")
			return nil
		}
	case *kafka.Error:
		return fmt.Errorf("kafka response error: %w", er)
	default:
		return UnknownEventError
	}
}

func (p *Producer) Close() {
	timeout := 3000
	p.producer.Flush(timeout)
	p.producer.Close()
}
