package handlers

import (
	"backend/internal/models"
	"strconv"
	// "backend/internal/repository"
	// "errors"
	"log/slog"
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"github.com/go-chi/render"
)

type GetAppHandler interface {
	GetAppById(appId int) (models.App, error)
	// SaveApp(app models.App) (models.App, error)
}

type Request struct {
	OrderUid string `json:"app_id"`
}

type Response struct {
	Status string     `json:"status"`
	Error  string     `json:"error,omitempty"`
	Order  models.App `json:"app,omitempty" validate:"required"`
}

func NewGetAppHandler(getAppHandler GetAppHandler, log *slog.Logger) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		const op = "handlers.getApp.new"

		log = log.With(
			slog.String("op", op),
			slog.String("request_id", middleware.GetReqID(r.Context())),
		)

		appId := chi.URLParam(r, "app_id")
		if appId == "" {
			log.Error("app_id parameter is required")
			render.JSON(w, r, Response{
				Status: "Error",
				Error:  "app_id parameter is required",
			})
			return
		}

		id, err := strconv.Atoi(appId)
		if err != nil {
			log.Error("invalid app_id format", slog.Any("request", id))
			render.JSON(w, r, Response{
				Status: "Error",
				Error:  "invalid app_id, must be a number",
			})
			return
		}

		log.Info("app_id received", slog.Any("request", id))

		order, err := getAppHandler.GetAppById(int(id))

		if err != nil {
			// TODO: добавить обработку разных ошибок, из repository.errors
		}
		log.Info("order received", slog.Any("order: ", appId))

		render.JSON(w, r, Response{
			Status: "Ok",
			Order:  order,
		})
	}
}
