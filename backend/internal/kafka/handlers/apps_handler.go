package handlers

import (
	"backend/internal/models"
	"backend/internal/repository"
	"encoding/json"
	"fmt"
	"log/slog"
)

type AppMsgHandler struct {
	rep *repository.Repository
	log *slog.Logger
}

func NewAppMsgHandler(rep *repository.Repository, log *slog.Logger) *AppMsgHandler {
	return &AppMsgHandler{rep: rep, log: log}
}

func (h *AppMsgHandler) HandleMessage(msg []byte) error {
	var app models.App
	if err := json.Unmarshal(msg, &app); err != nil {
		return fmt.Errorf("msg unmarshaling error: %w", err)
	}

	h.log.Info("received app: ", app.Name)

	if err := h.rep.SaveApp(app); err != nil {
		return fmt.Errorf("saving message error: %w", err)
	}

	h.log.Info("msg successfully sent")

	return nil
}
