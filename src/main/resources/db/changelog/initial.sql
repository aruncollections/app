--liquibase formatted sql

--changeset arun:initial:1
CREATE TABLE IF NOT EXISTS user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email_id VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  is_active TINYINT(1) NOT NULL DEFAULT 0,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_edited_by VARCHAR(255)
);

--changeset arun:initial:2
CREATE TABLE IF NOT EXISTS  role (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

--changeset arun:initial:3
CREATE TABLE IF NOT EXISTS  user_role (
  user_id int NOT NULL,
  role_id int NOT NULL
);

--changeset arun:initial:4
ALTER TABLE user_role ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_role ADD CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role (id);

--changeset arun:initial:5
CREATE TABLE IF NOT EXISTS investment_data (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  instrument VARCHAR(100) NOT NULL,
  invested_amount DOUBLE NOT NULL,
  updated_amount DOUBLE NOT NULL,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_edited_by VARCHAR(255)
);

--changeset arun:initial:7
ALTER TABLE investment_data ADD CONSTRAINT investment_data_user_fk FOREIGN KEY (user_id) REFERENCES user (id);

--changeset arun:initial:8
CREATE TABLE IF NOT EXISTS upload_log (
   id INT AUTO_INCREMENT PRIMARY KEY,
   investor_id VARCHAR(255),
   email_id VARCHAR(255),
   instrument VARCHAR(100),
   invested_amount DOUBLE,
   updated_amount DOUBLE,
   hash VARCHAR(500),
   uploaded_by VARCHAR(255),
   updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset arun:initial:9
CREATE INDEX idx_user_email_id ON user (email_id);
CREATE INDEX idx_upload_log_email_id ON upload_log (email_id);

--changeset arun:initial:10
CREATE TABLE IF NOT EXISTS file_hash_log (
   id INT AUTO_INCREMENT PRIMARY KEY,
   hash VARCHAR(500),
   uploaded_by VARCHAR(255),
   updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_file_hash_log ON file_hash_log (hash);