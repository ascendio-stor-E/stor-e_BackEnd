package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.storybooks.StoryBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "story")
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    public Story(String textContent, Integer pageNumber, String image, StoryBook storyBook) {
        this.textContent = textContent;
        this.pageNumber = pageNumber;
        this.image = image;
        this.storyBook = storyBook;
    }

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
}
