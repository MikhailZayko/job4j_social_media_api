create TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    subscriber_id INT NOT NULL REFERENCES users(id),
    target_id INT NOT NULL REFERENCES users(id)
);