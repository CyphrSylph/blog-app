package com.codeup.data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

// US6-F: Implement persistence for the User
@Entity
@Table(name="users")
// US5-A: Implement the User class
public class User {
    // Private fields for User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Email
    @NotEmpty
    @Column(nullable = false, length = 100)
    private String email;

    @ToString.Exclude
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private LocalDate createdAt;

    // Passes string value "ADMIN" to Hibernate instead of integer index
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    // US7: As a user, I can see the author of blog posts
    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private Collection<Post> posts;
}
