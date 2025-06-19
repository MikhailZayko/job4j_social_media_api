CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    content VARCHAR NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    created TIMESTAMP
);