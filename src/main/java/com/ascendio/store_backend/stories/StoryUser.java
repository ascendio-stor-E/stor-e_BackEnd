package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.storybooks.StoryBook;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
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

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "storyUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryBook> storyBooks = new ArrayList<>();
}
