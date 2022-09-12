// US5-B: Implement the UsersController
package com.codeup;

import data.Post;
import data.User;
import data.UserRole;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
// Make sure the class' @RequestMapping value is set to /api/users.
// IMPORTANT: register user needs a special endpoint /api/users/create
@RequestMapping(value = "/api/users", headers =  "Accept=application/json")
public class UsersController {
    // Private fields
    private List<User> users = new ArrayList<>();
    private long nextID = 1;

    @GetMapping
    public List<User> fetchUsers() { return users; }

    @GetMapping("/{id}")
    public User fetchUserByID(@PathVariable long id){
        // Search list of users and return matching user based on ID
        User user = findUserByID(id);
        // If matching user NOT found [equals null]
        if(user == null) {
            // Respond with error
            throw new RuntimeException("Simulation Glitch: user not found");
        }
        // Return matching user if found
        return user;
    }

    private User findUserByID(long id) {
        // For every user in the list of users
        for (User user: users) {
            // Compare user ID
            if(user.getId() == id) {
                // Return user if match found
                return user;
            }
        }
        // Return null if NOT match not found
        return null;
    }

    @PostMapping
    private void createUser(@RequestBody User newUser) {
        // Set and increment ID for new user
        newUser.setId(nextID);
        nextID++;
        // Add new user to list of posts
        users.add(newUser);
    }

    @PutMapping("/{id}")
    private void updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        // Search list of users and delete matching user based on ID
        User user = findUserByID(id);
        // If matching user NOT found [equals null]
        if(user == null) {
            System.out.println("Simulation Glitch: user not found");
        } else {
            // If matching username found [does NOT equal null]
            if(updatedUser.getUsername() != null) {
                // Update username
                user.setUsername(updatedUser.getUsername());
            }
            // If matching email found [does NOT equal null]
            if(updatedUser.getEmail() != null) {
                // Update email
                user.setEmail(updatedUser.getEmail());
            }
            // If matching password found [does NOT equal null]
            if(updatedUser.getPassword() != null) {
                // Update password
                user.setPassword(updatedUser.getPassword());
            }
            // Return updated user
            return;
        }
        // Respond with error
        throw new RuntimeException("Simulation Glitch: user not found");
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable Long id) {
        // Search list of users and delete matching user based on ID
        User user = findUserByID(id);
        // If matching user found [does NOT equal null]
        if(user != null) {
            // Remove user from list of users
            users.remove(user);
            return;
        }
        // Respond with error if matching user NOT found
        throw new RuntimeException("Simulation Glitch: source not found");
    }

}
