// US5-B: Implement the UsersController
package com.codeup;

import data.User;
import data.UserRole;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
// Make sure the class' @RequestMapping value is set to /api/users.
// IMPORTANT: register user needs a special endpoint /api/users/create
@RequestMapping(value = "/api/users", headers =  "Accept=application/json")
public class UsersController {
    // Private fields
    private final List<User> users = new ArrayList<>();
    private long nextID = 1;

    // US6-A: Make getAll and getById in UsersController
    @GetMapping("")
    public List<User> fetchUsers() { return users; }

    @GetMapping("/prep")
    private User fetchPrep() {
        return users.get(0);
    }

    @GetMapping("/{id}")
    public User fetchUserByID(@PathVariable long id){
        // Search list of users and return matching user based on ID
        User user = findByID(id);
        // If matching user NOT found [equals null]
        if(user == null) {
            // Respond with error
            throw new RuntimeException("Simulation Glitch: user not found");
        }
        // Return matching user if found
        return user;
    }
    private User findByID(long id) {
        // For every user in the list of users
        for (User user: users) {
            // Compare user ID
            if(user.getId() == id) {
                // Return user if match found
                return user;
            }
        }
        // Return null if match NOT found
        return null;
    }

    // US6-B: Make getByUsername and getByEmail
    @GetMapping("/username/{username}")
    private User fetchByUsername(@PathVariable String username) {
        // Search list of users and return matching user based on username
        User user = findByUsername(username);
        // If matching username NOT found [equals null]
        if(user == null) {
            // Respond with error
            throw new RuntimeException("Simulation Glitch: user not found");
        }
        // Return matching user if found
        return user;
    }
    private User findByUsername(String username) {
        // For every user in the list of users
        for(User user: users) {
            // Compare username
            if(user.getUsername().equals(username)) {
                // Return user if match found
                return user;
            }
        }
        // Return null if match NOT found
        return null;
    }

    @GetMapping("/email/{email}")
    private User fetchByEmail(@PathVariable String email) {
        // Search list of users and return matching user based on email
        User user = findByEmail(email);
        // If matching email NOT found [equals null]
        if(user == null) {
            // Respond with error
            throw new RuntimeException("Simulation Glitch: user not found");
        }
        // Return matching user if found
        return user;
    }
    private User findByEmail(String email) {
        // For every user in the list of users
        for(User user: users) {
            // Compare email
            if(user.getUsername().equals(email)) {
                // Return user if match found
                return user;
            }
        }
        // Return null if match NOT found
        return null;
    }

    @PostMapping("/create")
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
        User user = findByID(id);
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

    // US6-D: Make updatePassword in UsersController
    @PutMapping("/{id}/updatePassword")
    private void updatePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min = 3) @RequestParam String newPassword) {
        User user = findByID(id);
        if(user == null) {
            // Use ResponseStatusException instead of RuntimeException
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Simulation Glitch: user not found");
        }
        // Compare old password with saved password
        if(!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Simulation Glitch: password mismatch");
        }
        // Validate new password based on minimum length
        if(newPassword.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Simulation Glitch: minimum password length is 3 characters");
        }
        user.setPassword(newPassword);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable Long id) {
        // Search list of users and delete matching user based on ID
        User user = findByID(id);
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
