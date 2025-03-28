-- liquibase formatted sql

-- changeset amir:1743150624752-1
CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

-- changeset amir:1743150624752-2
CREATE TABLE users
(
    id            BIGINT                      NOT NULL,
    username      VARCHAR(128)                NOT NULL,
    fullname      VARCHAR(128)                NOT NULL,
    email         VARCHAR(255)                NOT NULL,
    password      VARCHAR(255)                NOT NULL,
    date_of_birth TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    gender        VARCHAR(255)                NOT NULL,
    uuid          UUID,
    is_active     BOOLEAN                     NOT NULL,
    is_delete     BOOLEAN                     NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    deleted_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset amir:1743150624752-3
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset amir:1743150624752-4
DROP SEQUENCE user_seq CASCADE;

