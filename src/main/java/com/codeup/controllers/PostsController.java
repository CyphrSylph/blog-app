package com.codeup.controllers;

import com.codeup.data.Category;
import com.codeup.data.Post;
import com.codeup.data.User;
import com.codeup.repositories.CategoryRepository;
import com.codeup.repositories.PostRepository;
import com.codeup.repositories.UserRepository;
import com.codeup.sevices.EmailService;
import com.codeup.utils.FieldHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
// Inject class dependencies vs instantiating them manually:
// Spring automatically creates the repository instance and INJECTS it into the controller via its constructor.
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {

    // Instance variable to receive repository instance
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private EmailService emailService;

    @GetMapping
    // postRepository is an instance variable and can be used by any controller method
    public List<Post> fetchPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostByID(@PathVariable long id){
        return postRepository.findById(id);
    }

    @PostMapping("")
    public void createPost(@RequestBody Post newPost) {

        // Set default author for new post
        User author = userRepository.findById(1L).get();
        // Set author to new post
        newPost.setAuthor(author);
        // Set categories to new post
        newPost.setCategories(new ArrayList<>());
        // Create new category objects
        Category cat1 = categoryRepository.findById(1L).get();
        Category cat2 = categoryRepository.findById(2L).get();
        Category cat3 = categoryRepository.findById(3L).get();
        // Add categories to new post
        newPost.getCategories().add(cat1);
        newPost.getCategories().add(cat2);
        newPost.getCategories().add(cat3);
        // Save categories to new post
        postRepository.save(newPost);
        // Send email notification of new post
        emailService.prepareAndSend(newPost,"Kudos","On the new post");
    }

    @PutMapping("/{id}")
    private void updatePost(@RequestBody Post updatedPost, @PathVariable Long id) {
        // Find selected post by id via postRepository
        Optional<Post> selectedPost = postRepository.findById(id);
        // Throw error if the selected post is not found
        if(selectedPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Simulation Glitch: post not found");
        }
        // Set post id if selected post is found
        updatedPost.setId(id);
        // Copy field values from updatedPost to selectedPost via BeanUtils
        BeanUtils.copyProperties(updatedPost, selectedPost.get(), FieldHelper.getNullPropertyNames(updatedPost));
        // Save and update selected post
        postRepository.save(selectedPost.get());
    }

    @DeleteMapping("/{id}")
    private void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}



//// PRE-SPRING CONTROLLER METHODS
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



