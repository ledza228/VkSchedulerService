DROP TABLE IF EXISTS order_lab CASCADE;
DROP TABLE IF EXISTS user_auth_token CASCADE;
DROP TABLE IF EXISTS user_creds CASCADE;

DROP sequence IF EXISTS order_sequence;

CREATE SEQUENCE order_sequence start 1 increment 1;
CREATE TABLE order_lab (id int4 not null, conversation_id int4, conversation_name varchar(255), date_time TIMESTAMP WITH TIME ZONE, is_active boolean, text varchar(255), owner_id varchar(255), primary key (id));
CREATE TABLE user_auth_token (id varchar(255) not null, auth_token varchar(1024), primary key (id));
CREATE TABLE user_creds (id varchar(255) not null, first_name varchar(255), last_name varchar(255), photo_max varchar(255), vk_token_id varchar(255), primary key (id));

ALTER TABLE IF EXISTS order_lab ADD CONSTRAINT labs_to_users FOREIGN KEY (owner_id) REFERENCES user_creds;
ALTER TABLE IF EXISTS user_creds ADD CONSTRAINT users_to_token FOREIGN KEY (vk_token_id) REFERENCES user_auth_token;
