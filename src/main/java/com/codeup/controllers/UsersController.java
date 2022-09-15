package com.codeup.controllers;
import com.codeup.data.User;
import com.codeup.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Size;

// Inject class dependencies vs instantiating them manually:
// Spring automatically creates the repository instance and INJECTS it into the controller via its constructor.
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces =  "application/json")
public class UsersController {

    private UserRepository userRepository;

    @GetMapping("")
    public List<User> fetchUsers() { return userRepository.findAll(); }

    @GetMapping("/prep")
    private Optional<User> fetchPrep() {
        return userRepository.findById(1L);
    }

    @GetMapping("/{id}")
    public Optional<User> fetchUserByID(@PathVariable long id){
        return userRepository.findById(id);
    }

//    @GetMapping("/username/{username}")
//    private User fetchByUsername(@PathVariable String username) {
//        // Search list of users and return matching user based on username
//        User user = findByUsername(username);
//        // If matching username NOT found [equals null]
//        if(user == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: user not found");
//        }
//        // Return matching user if found
//        return user;
//    }

//    @GetMapping("/email/{email}")
//    private User fetchByEmail(@PathVariable String email) {
//        // Search list of users and return matching user based on email
//        User user = findByEmail(email);
//        // If matching email NOT found [equals null]
//        if(user == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: user not found");
//        }
//        // Return matching user if found
//        return user;
//    }

    @PostMapping("/create")
    public void createUser(@RequestBody User newUser) {
        userRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        updatedUser.setId(id);
    }

    @PutMapping("/{id}/updatePassword")
    private void updatePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min = 3) @RequestParam String newPassword) {
        User user = userRepository.findById(id).get();
        if(!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Simulation Glitch: password mismatch");
        }
        if(newPassword.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Simulation Glitch: minimum password length is 3 characters");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}



// PRE-SPRING CONTROLLER METHODS
//// US5-B: Implement the UsersController
//@RestController
//// Make sure the class' @RequestMapping value is set to /api/users.
//// IMPORTANT: register user needs a special endpoint /api/users/create
//@RequestMapping(value = "/api/users", headers =  "Accept=application/json")
//public class UsersController {
//    // Private fields
//    private final List<User> users = new ArrayList<>();
//    private long nextID = 1;
//
//    // US6-A: Make getAll and getById in UsersController
//    @GetMapping("")
//    public List<User> fetchUsers() { return users; }
//
//    @GetMapping("/prep")
//    private User fetchPrep() {
//        return users.get(0);
//    }
//
//    @GetMapping("/{id}")
//    public User fetchUserByID(@PathVariable long id){
//        // Search list of users and return matching user based on ID
//        User user = findByID(id);
//        // If matching user NOT found [equals null]
//        if(user == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: user not found");
//        }
//        // Return matching user if found
//        return user;
//    }
//    private User findByID(long id) {
//        // For every user in the list of users
//        for (User user: users) {
//            // Compare user ID
//            if(user.getId() == id) {
//                // Return user if match found
//                return user;
//            }
//        }
//        // Return null if match NOT found
//        return null;
//    }
//
//    // US6-B: Make getByUsername and getByEmail
//    @GetMapping("/username/{username}")
//    private User fetchByUsername(@PathVariable String username) {
//        // Search list of users and return matching user based on username
//        User user = findByUsername(username);
//        // If matching username NOT found [equals null]
//        if(user == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: user not found");
//        }
//        // Return matching user if found
//        return user;
//    }
//    private User findByUsername(String username) {
//        // For every user in the list of users
//        for(User user: users) {
//            // Compare username
//            if(user.getUsername().equals(username)) {
//                // Return user if match found
//                return user;
//            }
//        }
//        // Return null if match NOT found
//        return null;
//    }
//
//    @GetMapping("/email/{email}")
//    private User fetchByEmail(@PathVariable String email) {
//        // Search list of users and return matching user based on email
//        User user = findByEmail(email);
//        // If matching email NOT found [equals null]
//        if(user == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: user not found");
//        }
//        // Return matching user if found
//        return user;
//    }
//    private User findByEmail(String email) {
//        // For every user in the list of users
//        for(User user: users) {
//            // Compare email
//            if(user.getEmail().equals(email)) {
//                // Return user if match found
//                return user;
//            }
//        }
//        // Return null if match NOT found
//        return null;
//    }
//
//    @PostMapping("/create")
//    private void createUser(@RequestBody User newUser) {
//        // Set and increment ID for new user
//        newUser.setId(nextID);
//        nextID++;
//        // Add new user to list of posts
//        users.add(newUser);
//    }
//
//    @PutMapping("/{id}")
//    public void updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
//        // Search list of users and delete matching user based on ID
//        User user = findByID(id);
//        // If matching user NOT found [equals null]
//        if(user == null) {
//            System.out.println("Simulation Glitch: user not found");
//        } else {
//            // If matching email found [does NOT equal null]
//            if(updatedUser.getEmail() != null) {
//                // Update email
//                user.setEmail(updatedUser.getEmail());
//            }
//            // Return email if found
//            return;
//        }
//        // Respond with error if NOT found
//        throw new RuntimeException("Simulation Glitch: user not found");
//    }
//
//    // US6-D: Make updatePassword in UsersController
//    @PutMapping("/{id}/updatePassword")
//    private void updatePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min = 3) @RequestParam String newPassword) {
//        User user = findByID(id);
//        if(user == null) {
//            // Use ResponseStatusException instead of RuntimeException
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Simulation Glitch: user not found");
//        }
//        // Compare old password with saved password
//        if(!user.getPassword().equals(oldPassword)) {
//            throw new RuntimeException("Simulation Glitch: password mismatch");
//        }
//        // Validate new password based on minimum length
//        if(newPassword.length() < 3) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Simulation Glitch: minimum password length is 3 characters");
//        }
//        user.setPassword(newPassword);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        // Search list of users and delete matching user based on ID
//        User user = findByID(id);
//        // If matching user found [does NOT equal null]
//        if(user != null) {
//            // Remove user from list of users
//            users.remove(user);
//            return;
//        }
//        // Respond with error if matching user NOT found
//        throw new RuntimeException("Simulation Glitch: source not found");
//    }
//
//}
