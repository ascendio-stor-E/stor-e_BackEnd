package com.ascendio.store_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "story_book")
public class StoryBook {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover_image")
    private String coverImage;

    //0 means draft , 1 means completed , 2 means favourite , 3 means deleted
    @Column(name = "status", nullable = false)
    private StoryBookStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private StoryUser storyUser;

    @OneToMany(mappedBy = "storyBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();

    public StoryBook() {
    }

    public StoryBook(UUID id, String title, String coverImage, StoryBookStatus status) {
        this.id = id;
        this.title = title;
        this.coverImage = coverImage;
        this.status = status;
    }

    public StoryBookStatus getStatus() {
        return status;
    }

    public void setStatus(StoryBookStatus status) {
        this.status = status;
    }

    public StoryUser getStoryUser() {
        return storyUser;
    }

    public void setStoryUser(StoryUser storyUser) {
        this.storyUser = storyUser;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

}

