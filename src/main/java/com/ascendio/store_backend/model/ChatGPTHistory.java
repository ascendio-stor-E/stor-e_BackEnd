package com.ascendio.store_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "chat_gpt_history", indexes = @Index(columnList = "conversation_id"))
public class ChatGPTHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "conversation_id", nullable = false)
    String conversationId;

    @Column(name = "content", nullable = false, length = 2000)
    String content;
    @Column(name = "role", nullable = false)
    String role;
    @Column(name = "created_at", nullable = false)
    Long createdAt;

    @ManyToOne
    @JoinColumn(name = "story_book_id")
    private StoryBook storyBook;


    public ChatGPTHistory(UUID id, String conversationId, String content, String role, Long createdAt, StoryBook storyBook) {
        this.id = id;
        this.conversationId = conversationId;
        this.content = content;
        this.role = role;
        this.createdAt = createdAt;
        this.storyBook = storyBook;
    }

    public ChatGPTHistory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    @Override
    public String toString() {
        return "ChatGPTHistory{" +
                "id=" + id +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
