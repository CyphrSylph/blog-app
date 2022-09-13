package data;
// Import lombok to utilize annotations
import lombok.*;
import java.util.Collection;
// Empty constructor imported from lombok
@NoArgsConstructor
// Full constructor imported from lombok
@AllArgsConstructor
// Getters & Setters imported from lombok
@Getter
@Setter
@ToString

public class Post {
    // Private Fields
    private Long id;
    private String title;
    private String content;
    // US7: As a user, I can see the author of blog posts
    private User author;
    // US9: As a user, I can view categories assigned to a post
    private Collection<Category> categories;

}
