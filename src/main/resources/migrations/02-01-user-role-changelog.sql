-- liquibase formatted sql

-- changeset amir:1743585302280-1
ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset amir:1743585302280-2
ALTER TABLE user_role
    ADD CONSTRAINT FK_USER_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset amir:1743585302280-3
ALTER TABLE user_role
    DROP COLUMN deleted_at;
ALTER TABLE user_role
    DROP COLUMN is_delete;
ALTER TABLE user_role
    DROP COLUMN updated_at;

