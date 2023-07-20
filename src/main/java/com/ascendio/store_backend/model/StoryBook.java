package com.ascendio.store_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private StoryUser storyUser;

    @JsonIgnore
    @OneToMany(mappedBy = "storyBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();

    public StoryBook() {
    }

    public StoryBook(UUID id, String title, String coverImage, Boolean status) {
        this.id = id;
        this.title = title;
        this.coverImage = coverImage;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

//    public StoryUser getUser() {
//        return storyUser;
//    }
//
//    public void setUser(StoryUser storyUser) {
//        this.storyUser = storyUser;
//    }

//    public List<Story> getStoryPages() {
//        return stories;
//    }
//
//    public void setStoryPages(List<Story> stories) {
//        this.stories = stories;
//    }
}

