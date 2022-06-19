CREATE TABLE conversations
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    agent_id BIGINT,
    user_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT pk_conversations PRIMARY KEY (id),
    CONSTRAINT FK_CONVERSATIONS_ON_AGENT FOREIGN KEY (agent_id) REFERENCES agents (id),
    CONSTRAINT FK_CONVERSATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);