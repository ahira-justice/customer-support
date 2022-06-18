CREATE TABLE authorities
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
    CONSTRAINT pk_authorities PRIMARY KEY (id),
    CONSTRAINT uc_authorities_name UNIQUE (name)
);

CREATE TABLE roles_authorities
(
    authority_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_roles_authorities PRIMARY KEY (authority_id, role_id),
    CONSTRAINT fk_roles_authorities_on_authority FOREIGN KEY (authority_id) REFERENCES authorities (id),
    CONSTRAINT fk_roles_authorities_on_role FOREIGN KEY (role_id) REFERENCES roles (id)
);