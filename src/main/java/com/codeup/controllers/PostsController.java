package com.codeup.controllers;

import com.codeup.data.Post;
import com.codeup.repositories.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostsController {

    private PostRepository postRepository;

    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> fetchPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostByID(@PathVariable long id){
        return postRepository.findById(id);
    }

    @PostMapping("")
    public void createPost(@RequestBody Post newPost) {
        postRepository.save(newPost);
    }

    @PutMapping("/{id}")
    private void updatePost(@RequestBody Post updatedPost, @PathVariable Long id) {
        updatedPost.setId(id);
        postRepository.save(updatedPost);
    }

    @DeleteMapping("/{id}")
    private void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}



//// PRE-SPRING CONTROLLER METHODS
//
//// @RestController registers the class with Spring's Dependency Injector and allows us to signify that a controller exists for the purpose of sending/receiving data
//@RestController
//@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
//public class PostsController {
//    // Private fields
//    private List<Post> posts = new ArrayList<>();
//    private long nextID = 1;
//
//    // @GetMapping acts a shortcut for @RequestMapping(method = RequestMethod.GET)
//    @GetMapping
//    public List<Post> fetchPosts() {
//        return posts;
//    }
//
//    @GetMapping("/{id}")
//    public Post fetchPostByID(@PathVariable long id){
//        // Search list of posts and return matching post based on ID
//        Post post = findPostByID(id);
//        // If matching post NOT found [equals null]
//        if(post == null) {
//            // Respond with error
//            throw new RuntimeException("Simulation Glitch: source not found");
//        }
//        // Return matching post if found
//        return post;
//    }
//
//    private Post findPostByID(long id) {
//        // For every post in the list of posts
//        for (Post post: posts) {
//            // Compare post ID
//            if(post.getId() == id) {
//                // Return if match found
//                return post;
//            }
//        }
//        // Return null if NOT match not found
//        return null;
//    }
//
//    // US2-A: Make createPost() & use @PostMapping to allow POST requests/responses to be handled in PostsController
//    @PostMapping("")
//    public void createPost(@RequestBody Post newPost) {
//        // Set ID for new post
//        newPost.setId(nextID);
//        // Use default author for new post
//        User defaultAuthor = new User();
//        defaultAuthor.setId(3);
//        defaultAuthor.setUsername("Default Author");
//        defaultAuthor.setEmail("defaultAuthor@glitch.com");
//        newPost.setAuthor(defaultAuthor);
//        // Create new category object
//        Category cat1 = new Category(1L,"CategoryA",null);
//        Category cat2 = new Category(2L,"CategoryB",null);
//        Category cat3 = new Category(3L,"CategoryA",null);
//        // Set category to new post
//        newPost.setCategories(new ArrayList<>());
//        // Add category to new post
//        newPost.getCategories().add(cat1);
//        newPost.getCategories().add(cat2);
//        newPost.getCategories().add(cat3);
//        // Increment ID for new post
//        nextID++;
//        // Add new post to list of posts
//        posts.add(newPost);
//    }
//
//    // US3-A: Make updatePost() & use @PutMapping to allow PUT requests/responses to be handled in PostsController
//    @PutMapping("/{id}")
//    private void updatePost(@RequestBody Post updatedPost, @PathVariable Long id) {
//        // Search list of posts and delete matching post based on ID
//        Post post = findPostByID(id);
//        // If matching post NOT found [equals null]
//        if(post == null) {
//            System.out.println("Simulation Glitch: source not found");
//        } else {
//            // If matching post title found [does NOT equal null]
//            if(updatedPost.getTitle() != null) {
//                // Update post title
//                post.setTitle(updatedPost.getTitle());
//            }
//            // If matching post content found [does NOT equal null]
//            if(updatedPost.getContent() != null) {
//                // Update post content
//                post.setContent(updatedPost.getContent());
//            }
//            // Return updated post
//            return;
//        }
//        // Respond with error
//        throw new RuntimeException("Post not found");
//    }
//
//    // US4-A: Make deletePost() & @DeleteMapping to allow DELETE requests/responses to be handled in PostsController
//    @DeleteMapping("/{id}")
//    private void deletePost(@PathVariable Long id) {
//        // Search list of posts and delete matching post based on ID
//        Post post = findPostByID(id);
//        // If matching post found [does NOT equal null]
//        if(post != null) {
//            // Remove post from list of posts
//            posts.remove(post);
//            return;
//        }
//        // Respond with error if matching post NOT found
//        throw new RuntimeException("Simulation Glitch: source not found");
//    }
//}



