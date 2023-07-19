package com.ascendio.store_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Favourite {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private StoryUser storyUser;

    @OneToMany(mappedBy = "favourite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryBook> storyBookId = new ArrayList<>();

    public Favourite() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StoryUser getStoryUser() {
        return storyUser;
    }

    public void setStoryUser(StoryUser storyUser) {
        this.storyUser = storyUser;
    }

    public List<StoryBook> getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(List<StoryBook> storyIds) {
        this.storyBookId = storyIds;
    }
}
