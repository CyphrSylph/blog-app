package com.codeup.controllers;

import com.codeup.data.Category;
import com.codeup.data.Post;
import com.codeup.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
// US9-C, US10-C: Create the CategoriesController
@RestController
@RequestMapping(value = "/api/categories", headers = "Accept=application/json")
public class CategoriesController {

    private CategoryRepository categoryRepository;

    // Search list of categories and return matching category based on name
    @GetMapping
    private List<Category> fetchByCategory(@RequestParam String categoryName) {
        return categoryRepository.findAll();

        // PRE-SPRING METHOD
//        Category category = new Category(1L, categoryName, null);
//        ArrayList<Post> defaultPosts = new ArrayList<>();
//        defaultPosts.add(new Post(1L, "A","AAA",null, null));
//        defaultPosts.add(new Post(2L,"B","BBB",null,null));
//        defaultPosts.add(new Post(3L,"C","CCC",null,null));
//        category.setPosts(defaultPosts);
//        return category;
    }

    @GetMapping("/search")
    private Category fetchCategoryByCategoryName(@RequestParam String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if(category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Simulation Glitch: category not found");
        }
        return category;
    }
}
