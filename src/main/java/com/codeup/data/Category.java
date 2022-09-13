package com.codeup.data;
import lombok.*;

import java.util.Collection;

// Empty constructor
@NoArgsConstructor
// Full constructor
@AllArgsConstructor
// Getters & Setters
@Getter
@Setter
@ToString

// US9-A, US10-A: Create the Category class
public class Category {
    private Long ID;
    private String name;
    // Many-to-many relationship
    private Collection<Post> posts;
}
