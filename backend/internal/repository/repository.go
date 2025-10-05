package repository

import (
	"backend/internal/models"
	"fmt"
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

func (r *Repository) GetAppById(appId int) (models.App, error) {
	var app models.App
	var screenshots []models.Screenshot
	tx, err := r.db.Beginx()
	if err != nil {
		return app, fmt.Errorf("beginning transaction error: %w", err)
	}
	defer tx.Rollback()

	query := `SELECT * FROM apps WHERE id = $1`
	if err := tx.Get(&app, query, appId); err != nil {
		return app, fmt.Errorf("error getting app: %w", err)
	}

	query = `SELECT * FROM screenshots WHERE app_id = $1`
	if err := tx.Select(&screenshots, query, appId); err != nil {
		return app, fmt.Errorf("error getting screenshots: %w", err)
	}
	app.Screenshots = screenshots

	if err := tx.Commit(); err != nil {
		return app, fmt.Errorf("commit error: %w", err)
	}

	return app, nil
}

func (r *Repository) SaveApp(app models.App) error {
	tx, err := r.db.Beginx()
	if err != nil {
		return fmt.Errorf("transaction begin error: %w", err)
	}
	defer tx.Rollback()

	query := `INSERT INTO apps (id, name, icon_url, short_description, full_description, category_id, developer, age_rating, apk_url) 
						VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9)`

	if _, err := tx.Exec(query, app.ID, app.Name, app.IconURL, app.ShortDescription,
		app.FullDescription, app.CategoryID, app.Developer, app.AgeRating, app.ApkURL); err != nil {
		return fmt.Errorf("inserting app error: %w", err)
	}

	query = `INSERT INTO screenshots (id, app_id, url) VALUES ($1, $2, $3)`
	for _, s := range app.Screenshots {
		if _, err := tx.Exec(query, s.ID, app.ID, s.URL); err != nil {
			return fmt.Errorf("error inserting screenshot: %w", err)
		}
	}

	if err := tx.Commit(); err != nil {
		return fmt.Errorf("commit error: %w", err)
	}

	return nil
}
