--liquibase formatted sql

--changeset arun:initial-data:1
INSERT INTO role (name) VALUES ('USER');
INSERT INTO role (name) VALUES ('EDITOR');
INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('ROOT');

--changeset arun:initial-data:2
INSERT INTO user (email, password, first_name, is_active) VALUES ('admin@app.com', '$2a$10$jOBkDRw6PwszynOYMoW/J.oc6Av4kxAL2vCNz25WGS43r.nvJ75kW', 'Admin', 1);
INSERT INTO user (email, password, first_name, is_active) VALUES ('root@app.com', '$2a$10$jOBkDRw6PwszynOYMoW/J.oc6Av4kxAL2vCNz25WGS43r.nvJ75kW', 'Root User', 1);

--changeset arun:initial-data:3
INSERT INTO user_role VALUES (1,3);