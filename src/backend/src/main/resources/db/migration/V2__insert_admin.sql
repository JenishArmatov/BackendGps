INSERT INTO roles (id, role_name) VALUES (1, 'ADMIN') ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, username, first_name, last_name, phone, email, password, is_active)
VALUES (
           1,
           'admin',
           'Admin',
           'User',
           '+1234567890',
           'admin@example.com',
           '$2a$08$xGIHs5Vk6N2JQI/jsPKUqencjXwcrdJDSmYXVQWtlqyfoUVA8S5Cu',
           TRUE
       ) ON CONFLICT (id) DO NOTHING;

INSERT INTO m2m_users_roles (user_id, role_id) VALUES (1, 1) ON CONFLICT (user_id, role_id) DO NOTHING;
