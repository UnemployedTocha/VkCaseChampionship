package main

import (
	"backend/internal/http-server/handlers"
	"backend/internal/repository"
	"log/slog"
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
	_ = repo
	log.Info("Storage successfully started")

	// init router

	router := chi.NewRouter()

	router.Use(middleware.RequestID)
	router.Use(middleware.Logger)
	router.Use(middleware.Recoverer)
	router.Use(middleware.URLFormat)

	// router.Route("/app", func(r chi.Router) {
	// 	r.Get("/{}", handlers.NewGetAppHandler(repo, log)) // GET /app/{app_id} - конкретное приложение
	// })

	//init server

}

func setupLogger() (log *slog.Logger) {
	log = slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{Level: slog.LevelInfo}))
	return
}
