package data;
import lombok.*;
import javax.management.relation.Role;
import java.time.LocalDate;

// US5-A: Implement the User class

// Empty constructor
@NoArgsConstructor
// Full constructor
@AllArgsConstructor
// Getters & Setters
@Getter
@Setter
@ToString

public class User {
    // Private fields for User
    private long id;
    private String username;
    private String email;
    private String password;
    private LocalDate createdAt;
    private UserRole role;
}
