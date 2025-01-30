CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       phone VARCHAR(20) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE m2m_users_roles (
                                 user_id INT REFERENCES users(id) ON DELETE CASCADE,
                                 role_id INT REFERENCES roles(id) ON DELETE CASCADE,
                                 PRIMARY KEY (user_id, role_id)
);