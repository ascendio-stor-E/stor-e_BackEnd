package com.ascendio.store_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "story")
public class Story {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "text_content", nullable = false, length = 2000)
    private String textContent;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "image", length = 2000)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_book_id")
    private StoryBook storyBook;

    public Story(String textContent, Integer pageNumber, String image, StoryBook storyBook) {
        this.textContent = textContent;
        this.pageNumber = pageNumber;
        this.image = image;
        this.storyBook = storyBook;
    }

    public Story() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

}
