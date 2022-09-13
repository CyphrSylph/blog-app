package com.codeup.data;
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
    @Column(nullable = false)
    private String content;
    // The @Transient JPA annotation prevents fields from mapping to database columns
    // US7: As a user, I can see the author of blog posts
    @Transient
    private User author;
    // US9: As a user, I can view categories assigned to a post
    @Transient
    private Collection<Category> categories;
}
