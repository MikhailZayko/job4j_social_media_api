CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    file_path VARCHAR NOT NULL,
    post_id INTEGER NOT NULL REFERENCES posts(id)
);