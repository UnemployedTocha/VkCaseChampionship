package models

type App struct {
	ID               int          `json:"id" db:"id"`
	Name             string       `json:"name" db:"name"`
	IconURL          string       `json:"icon_url" db:"icon_url"`
	ShortDescription string       `json:"short_description" db:"short_description"`
	FullDescription  string       `json:"full_description" db:"full_description"`
	CategoryID       int64        `json:"category_id" db:"category_id"`
	Developer        string       `json:"developer" db:"developer"`
	AgeRating        string       `json:"age_rating" db:"age_rating"`
	ApkURL           string       `json:"apk_url" db:"apk_url"`
	Screenshots      []Screenshot `json:"screenshots"`
}
