package com.codeup;

import data.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// @RestController registers the class with Spring's Dependency Injector and allows us to signify that a controller exists for the purpose of sending/receiving data
@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {

    // @GetMapping acts a shortcut for @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Post> fetchAll() {
        // Create new array list for posts, add posts to the list, and return the list
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L,"LOG I", "Tales from the Dawn"));
        posts.add(new Post(2L,"LOG II", "A Friendship of Record"));
        posts.add(new Post(3L,"LOG III", "In Pursuit of Knowledge"));
        return posts;
    }

    @GetMapping("{id}")
    public Post fetchById(@PathVariable long id){
        // Fetch posts from array list and return them
        return switch ((int) id) {
            case 1 -> new Post(1L, "LOG I", "Tales from the Dawn");
            case 2 -> new Post(2L, "LOG II", "A Friendship of Record");
            case 3 -> new Post(3L, "LOG III", "In Pursuit of Knowledge");
            default ->
                // Respond with 404 error
                    throw new RuntimeException("Simulation Glitch: source not found");
        };
    }

    // US2-A: Make createPost() & use @PostMapping to allow POST requests/responses to be handled in PostsController
    @PostMapping
    private void createPost(@RequestBody Post createPost) {
        System.out.println(createPost);
    }

    // US3-A: Make updatePost() & use @PutMapping to allow PUT requests/responses to be handled in PostsController
    @PutMapping("{id}")
    private void updatePost(@PathVariable Long id, @RequestBody Post post) {}

    // US4-A: Make deletePost() & @DeleteMapping to allow DELETE requests/responses to be handled in PostsController
    @DeleteMapping("{id}")
    private void deletePost(@PathVariable Long id) {}
}

