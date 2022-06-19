CREATE TABLE messages
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NOT NULL,
    last_modified_on datetime NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    conversation_id  BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    body LONGTEXT NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id),
    CONSTRAINT FK_MESSAGES_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversations (id),
    CONSTRAINT FK_MESSAGES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);