package repository

import (
	"log/slog"
	"os"

	"github.com/jmoiron/sqlx"
)

type Repository struct {
	db *sqlx.DB
}

func NewRepository(log *slog.Logger) (*Repository, error) {
	db := NewDb(Config{
		Host:     os.Getenv("POSTGRES_HOST"),
		Port:     os.Getenv("POSTGRES_INTERNAL_PORT"),
		Username: os.Getenv("POSTGRES_USER"),
		Password: os.Getenv("POSTGRES_PASSWORD"),
		DBName:   os.Getenv("POSTGRES_DB"),
		SSLMode:  os.Getenv("SSL_MODE"),
	}, log)

	return &Repository{db: db}, nil
}
