package data;
import lombok.*;
import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Collection;


// Empty constructor
@NoArgsConstructor
// Full constructor
@AllArgsConstructor
// Getters & Setters
@Getter
@Setter
@ToString

// US5-A: Implement the User class
public class User {
    // Private fields for User
    private long id;
    private String username;
    private String email;
    private String password;
    private LocalDate createdAt;
    private UserRole role;
    // US7: As a user, I can see the author of blog posts
    private Collection<Post> posts;
}
