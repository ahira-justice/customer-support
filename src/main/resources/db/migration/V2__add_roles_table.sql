CREATE TABLE roles
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_system BIT(1) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uc_roles_name UNIQUE (name)
);

ALTER TABLE users
    ADD role_id BIGINT NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);