CREATE TABLE IF NOT EXISTS users (
                                    id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS tasks (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(100) NOT NULL,
    status VARCHAR(255),
    creator_id BIGINT,
    FOREIGN KEY (creator_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS task_assignees (
                                              task_id BIGINT,
                                              user_id BIGINT,
                                              PRIMARY KEY (task_id, user_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );