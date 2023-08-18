--liquibase formatted sql

--changeset arun:initial:1
CREATE TABLE IF NOT EXISTS user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  is_active TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_edited_by VARCHAR(255)
);

-- changeset arun:initial:2
CREATE TABLE IF NOT EXISTS  role (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

-- changeset arun:initial:3
CREATE TABLE IF NOT EXISTS  user_role (
  user_id int NOT NULL,
  role_id int NOT NULL
);

-- changeset arun:initial:4
ALTER TABLE user_role ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_role ADD CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role (id);