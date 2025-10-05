package main

import (
	"backend/internal/http-server/handlers"
	k "backend/internal/kafka"
	"backend/internal/repository"
	"log/slog"
	"net/http"
	"os"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	_ "github.com/lib/pq"
)

func main() {

	// setup logger

	log := setupLogger()
	log.Info("Starting backend")

	// init storage

	repo, err := repository.NewRepository(log)

	if err != nil {
		log.Error("Failed to connect to database")
		os.Exit(1)
	}
	log.Info("Storage successfully started")

	// start kafka consumer
	// handlers := map[string]*k.Handler{"app_topic": han.NewAppMsgHandler(repo, log)}
	consumer, err := k.NewConsumer("kafka:29091", repo, []string{"app_topic", "category_topic", "screenshot_topic"}, "123", log)
	log.Info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
	go consumer.Start()

	// init router
	router := chi.NewRouter()

	router.Use(middleware.RequestID)
	router.Use(middleware.Logger)
	router.Use(middleware.Recoverer)
	router.Use(middleware.URLFormat)

	// setup routes
	router.Route("/app", func(r chi.Router) {
		r.Get("/{app_id}", handlers.NewGetAppHandler(repo, log)) // GET /app/{app_id}
	})

	// init http server
	srv := &http.Server{
		Addr:    ":8089",
		Handler: router,
	}

	log.Info("HTTP server started on port 8098")
	if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
		log.Error("HTTP server error", slog.Any("err", err))
	}

}

func setupLogger() (log *slog.Logger) {
	log = slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{Level: slog.LevelInfo}))
	return
}
