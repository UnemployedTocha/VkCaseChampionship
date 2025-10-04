CREATE TABLE categories
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE apps
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    icon_url TEXT,
    short_description TEXT,
    full_description TEXT,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL,
    developer VARCHAR(100),
    age_rating VARCHAR(10),
    apk_url TEXT
);

CREATE TABLE screenshots
 (
    id SERIAL PRIMARY KEY,
    app_id INT NOT NULL REFERENCES apps(id) ON DELETE CASCADE,
    url TEXT NOT NULL
);

