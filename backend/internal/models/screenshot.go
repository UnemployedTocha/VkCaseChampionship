package models

type Screenshot struct {
	ID    int    `json:"id" db:"id"`
	AppID int    `json:"app_id" db:"app_id"`
	URL   string `json:"url" db:"url"`
}
