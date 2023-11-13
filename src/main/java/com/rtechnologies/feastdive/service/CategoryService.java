package com.rtechnologies.feastdive.service;

import com.rtechnologies.feastdive.model.Category;
import com.rtechnologies.feastdive.model.Customer;
import com.rtechnologies.feastdive.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        Optional<Category> existingCustomer = categoryRepository.findByCategoryName(category.getCategoryName());
        if(!existingCustomer.isPresent()) {
            return categoryRepository.save(category);
        } else{
            throw new RuntimeException("Category already exists");
        }
    }

    public void deleteCategory(String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryName);
        if(existingCategory.isPresent()) {
            categoryRepository.delete(existingCategory.get());
        } else{
            throw new NotFoundException("Category not found");
        }
    }

    public void updateCategory(String previousCategory, String updatedCategory){
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(previousCategory);
        if(existingCategory.isPresent()) {
            existingCategory.get().setCategoryName(updatedCategory);
            categoryRepository.save(existingCategory.get());
        }
        throw new NotFoundException("Category not found");
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
