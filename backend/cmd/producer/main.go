package main

import (
	k "backend/internal/kafka"
	"backend/internal/models"
	"encoding/json"
	"fmt"
	"log/slog"
	"os"
)

const (
	topic = "some_topic"
)

var apps_path = []string{"testdata/apps/app1.json", "testdata/apps/app2.json", "testdata/apps/app3.json",
	"testdata/apps/app4.json", "testdata/apps/app5.json", "testdata/apps/app6.json",
	"testdata/apps/app7.json", "testdata/apps/app8.json", "testdata/apps/app9.json",
	"testdata/apps/app10.json"}
var ctgry_path = []string{"testdata/categories/category1.json", "testdata/categories/category2.json", "testdata/categories/category3.json",
	"testdata/categories/category5.json", "testdata/categories/category5.json", "testdata/categories/category6.json",
	"testdata/categories/category7.json", "testdata/categories/category8.json", "testdata/categories/category9.json",
	"testdata/categories/category10.json"}
var scrnsht_path = []string{"testdata/screenshots/screenshot1.json", "testdata/screenshots/screenshot2.json",
	"testdata/screenshots/screenshot3.json", "testdata/screenshots/screenshot4.json", "testdata/screenshots/screenshot5.json",
	"testdata/screenshots/screenshot6.json", "testdata/screenshots/screenshot7.json", "testdata/screenshots/screenshot8.json",
	"testdata/screenshots/screenshot9.json", "testdata/screenshots/screenshot10.json", "testdata/screenshots/screenshot11.json",
	"testdata/screenshots/screenshot12.json", "testdata/screenshots/screenshot13.json", "testdata/screenshots/screenshot14.json",
	"testdata/screenshots/screenshot15.json"}

func main() {
	log := setupLogger()

	producer, err := k.NewProducer("kafka:29091", log)
	if err != nil {
		log.Error("producer creation failed")
		os.Exit(1)
	}
	defer producer.Close()

	// time.Sleep(3)

	apps, err := readApps()
	if err != nil {
		log.Error("Reading apps failed((9((9")
	}

	for _, app := range apps {
		err = producer.ProduceApp(app, topic)

		if err != nil {
			fmt.Println("App producing error :(((")
		}
	}

	categories, err := readCategories()
	if err != nil {
		log.Error("Reading categories failed((9((9")
	}

	for _, ctgry := range categories {
		err = producer.ProduceCategory(ctgry, topic)

		if err != nil {
			fmt.Println("Category producing error :(((")
		}
	}

	screenshots, err := readScreenshots()
	if err != nil {
		log.Error("Reading screenshots failed((9((9")
	}

	for _, scrnsht := range screenshots {
		err = producer.ProduceScreenshot(scrnsht, topic)

		if err != nil {
			fmt.Println("Screenshot producing error :(((")
		}
	}

}

func readApps() ([]models.App, error) {
	var apps []models.App
	for _, path := range apps_path {
		data, err := os.ReadFile(path)

		if err != nil {
			return nil, fmt.Errorf("reading json from file error")
		}

		var app models.App
		err = json.Unmarshal(data, &app)

		if err != nil {
			return nil, fmt.Errorf("json unmarshalling error")
		}

		apps = append(apps, app)
	}

	fmt.Printf("Successfully parsed apps!!!11!")
	return apps, nil
}

func readCategories() ([]models.Category, error) {
	var categories []models.Category
	for _, path := range ctgry_path {
		data, err := os.ReadFile(path)

		if err != nil {
			return nil, fmt.Errorf("reading json from file error")
		}

		var ctgry models.Category
		err = json.Unmarshal(data, &ctgry)

		if err != nil {
			return nil, fmt.Errorf("json unmarshalling error")
		}

		categories = append(categories, ctgry)
	}

	fmt.Printf("Successfully parsed categories!!!11!")
	return categories, nil
}

func readScreenshots() ([]models.Screenshot, error) {
	var screenshots []models.Screenshot
	for _, path := range scrnsht_path {
		data, err := os.ReadFile(path)

		if err != nil {
			return nil, fmt.Errorf("reading json from file error")
		}

		var scrnsht models.Screenshot
		err = json.Unmarshal(data, &scrnsht)

		if err != nil {
			return nil, fmt.Errorf("json unmarshalling error")
		}

		screenshots = append(screenshots, scrnsht)
	}

	fmt.Printf("Successfully parsed screenshots!!!11!")
	return screenshots, nil
}

func setupLogger() (log *slog.Logger) {
	log = slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{Level: slog.LevelInfo}))
	return
}
