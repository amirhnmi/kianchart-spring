-- liquibase formatted sql

-- changeset amir:1743318555201-1
CREATE TABLE permissions
(
    id          BIGINT                      NOT NULL,
    name        VARCHAR(128)                NOT NULL,
    slug        VARCHAR(128)                NOT NULL,
    description VARCHAR(512)                NOT NULL,
    is_delete   BOOLEAN                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

-- changeset amir:1743318555201-2
CREATE TABLE role_permissions
(
    id            BIGINT                      NOT NULL,
    role_id       BIGINT                      NOT NULL,
    permission_id BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (id)
);

-- changeset amir:1743318555201-3
CREATE TABLE roles
(
    id          BIGINT                      NOT NULL,
    name        VARCHAR(128)                NOT NULL,
    slug        VARCHAR(128)                NOT NULL,
    description VARCHAR(512)                NOT NULL,
    is_delete   BOOLEAN                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

-- changeset amir:1743318555201-4
ALTER TABLE permissions
    ADD CONSTRAINT uc_permissions_slug UNIQUE (slug);

-- changeset amir:1743318555201-5
ALTER TABLE roles
    ADD CONSTRAINT uc_roles_slug UNIQUE (slug);

-- changeset amir:1743318555201-6
ALTER TABLE role_permissions
    ADD CONSTRAINT FK_ROLE_PERMISSIONS_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permissions (id);

-- changeset amir:1743318555201-7
ALTER TABLE role_permissions
    ADD CONSTRAINT FK_ROLE_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

