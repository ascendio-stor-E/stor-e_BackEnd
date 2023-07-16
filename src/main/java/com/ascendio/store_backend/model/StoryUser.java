package com.ascendio.store_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "story_user")
public class StoryUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "storyUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryBook> storyBooks = new ArrayList<>();

    public StoryUser() {
    }

    public StoryUser(UUID id, String name, String email, List<StoryBook> storyBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.storyBooks = storyBooks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<StoryBook> getStoryBooks() {
        return storyBooks;
    }

    public void setStoryBooks(List<StoryBook> storyBooks) {
        this.storyBooks = storyBooks;
    }
}
