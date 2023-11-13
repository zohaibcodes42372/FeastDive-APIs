package com.rtechnologies.feastdive.repository;

import com.rtechnologies.feastdive.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurantId(long restaurantId);
    List<MenuItem> findAllByRestaurantIdAndCategoryId(long restaurantId, long categoryId);
}
