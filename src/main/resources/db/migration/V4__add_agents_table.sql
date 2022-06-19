CREATE TABLE agents
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    user_id BIGINT NULL,
    CONSTRAINT pk_agents PRIMARY KEY (id),
    CONSTRAINT FK_AGENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);