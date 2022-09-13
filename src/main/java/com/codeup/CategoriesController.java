package com.codeup;

import data.Category;
import data.Post;
import data.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// US9-C, US10-C: Create the CategoriesController
@RestController
@RequestMapping(value = "/api/categories", headers = "Accept=application/json")
public class CategoriesController {

    // Search list of categories and return matching category based on name
    @GetMapping
    private Category fetchByCategory(@RequestParam String categoryName) {
        Category category = new Category(1L, categoryName, null);
        ArrayList<Post> defaultPosts = new ArrayList<>();
        defaultPosts.add(new Post(1L, "A","AAA",null, null));
        defaultPosts.add(new Post(2L,"B","BBB",null,null));
        defaultPosts.add(new Post(3L,"C","CCC",null,null));
        category.setPosts(defaultPosts);
        return category;
    }
}
