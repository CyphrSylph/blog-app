package com.codeup.data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.util.Collection;

// Empty constructor imported from lombok
@NoArgsConstructor
// Full constructor imported from lombok
@AllArgsConstructor
// Getters & Setters imported from lombok
@Getter
@Setter
@ToString

// US12: Creation of and changes to posts will be stored
@Entity
@Table(name="posts")
public class Post {

    // The @Id JPA annotation defines the primary key [private Long id]
    @Id
    // The @GeneratedValue JPA annotation auto increments the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The @Column JPA annotation specifies the details of the associated property being mapped
    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1024)
    private String content;

    // US7: As a user, I can see the author of blog posts
    @ManyToOne
    @JsonIgnoreProperties({"posts","password"})
    private User author;

    // US9: As a user, I can view categories assigned to a post
    // The @Transient JPA annotation prevents fields from mapping to database columns
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            targetEntity = Category.class)
    @JoinTable(
            name = "post_category",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name="category_id", nullable = false, updatable = false)},
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    @JsonIgnoreProperties("posts")
    private Collection<Category> categories;
}
