CREATE TABLE IF NOT EXISTS tasks
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    title           VARCHAR(200)          NOT NULL,
    description     VARCHAR(200)          NOT NULL,
    status          VARCHAR(200),
    expiration_date TIMESTAMP             NULL,
    user_id         BIGINT
);