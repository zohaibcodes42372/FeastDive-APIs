package com.rtechnologies.feastdive.controller;

import com.rtechnologies.feastdive.model.Category;
import com.rtechnologies.feastdive.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/create")
    @ApiOperation(value = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created successfully"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Category already exists")
    })
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @DeleteMapping("/delete/{categoryName}")
    @ApiOperation(value = "Delete a category by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category deleted successfully"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body("Category created successfully");
    }

    @PutMapping("/update/{previousCategory}/{updatedCategory}")
    @ApiOperation(value = "Update a category name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category updated successfully"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<?> updateCategory(
            @PathVariable String previousCategory,
            @PathVariable String updatedCategory
    ) {
        categoryService.updateCategory(previousCategory,updatedCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully");
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "No categories found")
    })
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }
}
