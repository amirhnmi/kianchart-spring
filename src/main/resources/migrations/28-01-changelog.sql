-- liquibase formatted sql

-- changeset amir:1743150840369-1
ALTER TABLE users
    DROP COLUMN date_of_birth;

-- changeset amir:1743150840369-2
ALTER TABLE users
    ADD date_of_birth date NOT NULL;

