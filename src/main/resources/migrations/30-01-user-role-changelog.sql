-- liquibase formatted sql

-- changeset amir:1743319320577-1
CREATE SEQUENCE IF NOT EXISTS user_role_seq START WITH 1 INCREMENT BY 50;

-- changeset amir:1743319320577-2
CREATE TABLE user_role
(
    id         BIGINT                      NOT NULL,
    user_id    BIGINT                      NOT NULL,
    role_id    BIGINT                      NOT NULL,
    is_delete  BOOLEAN                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user_role PRIMARY KEY (id)
);

-- changeset amir:1743319320577-3
ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset amir:1743319320577-4
ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

