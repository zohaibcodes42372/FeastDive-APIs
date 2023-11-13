package com.rtechnologies.feastdive.service;

import com.rtechnologies.feastdive.model.Category;
import com.rtechnologies.feastdive.model.MenuItem;
import com.rtechnologies.feastdive.model.Restaurant;
import com.rtechnologies.feastdive.repository.CategoryRepository;
import com.rtechnologies.feastdive.repository.MenuItemRepository;
import com.rtechnologies.feastdive.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public MenuItem addItem(MenuItem menuItem) {
        if(menuItem == null) {
            throw new RuntimeException("Please, fill all data");
        }
        Optional<Restaurant> restaurant = restaurantRepository.findById(menuItem.getRestaurantId());
        if(!restaurant.isPresent()) {
            throw new RuntimeException("Restaurant doesn't exists. Please, register the restaurant");
        }

        Optional<Category> category = categoryRepository.findById(menuItem.getCategoryId());
        if(!category.isPresent()){
            throw new RuntimeException("Category doesn't exists. Please, select the valid category");
        }

        try {
            return menuItemRepository.save(menuItem);
        } catch (Exception e) {
            throw new RuntimeException("Item already exists in the menu");
        }
    }

    public void deleteItem(long menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
        if(!menuItem.isPresent()) {
            throw new NotFoundException("Item doesn't exists in the menu");
        }
        menuItemRepository.delete(menuItem.get());
    }

    public MenuItem updateMenuItem(long menuItemId, MenuItem updatedMenuItem) {
        if(!checkIfMenuItemExists(menuItemId)) {
            throw new NotFoundException("Item doesn't exists in the menu");
        }

        Optional<Restaurant> restaurant = restaurantRepository.findById(updatedMenuItem.getRestaurantId());
        if(!restaurant.isPresent()) {
            throw new RuntimeException("Restaurant doesn't exists. Please, register the restaurant");
        }

        Optional<Category> category = categoryRepository.findById(updatedMenuItem.getCategoryId());
        if(!category.isPresent()){
            throw new RuntimeException("Category doesn't exists. Please, select the valid category");
        }

        updatedMenuItem.setId(menuItemId);
        return menuItemRepository.save(updatedMenuItem);
    }

    public MenuItem findMenuItemDetails(long menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
        if(!menuItem.isPresent()) {
            throw new NotFoundException("Item doesn't exists in the menu");
        }
        return menuItem.get();
    }

    public List<MenuItem> findAllMenuItemsByRestaurant(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(!restaurant.isPresent()) {
            throw new NotFoundException("Restaurant doesn't exists. Please, register the restaurant");
        }
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(restaurantId);
        if(menuItems.isEmpty()) {
            throw new RuntimeException("No items found in the list");
        }
        return menuItems;
    }

    public List<MenuItem> findAllMenuItemsByRestaurantsAndCategory(long restaurantId, long categoryId) {
        List<MenuItem> menuItems =
                menuItemRepository.findAllByRestaurantIdAndCategoryId(restaurantId, categoryId);
        if(menuItems.isEmpty()) {
            throw new RuntimeException("No items found in the list");
        }
        return menuItems;
    }
    private boolean checkIfMenuItemExists(long menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
        if(!menuItem.isPresent()) {
            return false;
        }
        return true;
    }
}
