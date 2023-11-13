package com.rtechnologies.feastdive.service;

import com.rtechnologies.feastdive.dto.RestaurantResponse;
import com.rtechnologies.feastdive.model.Customer;
import com.rtechnologies.feastdive.model.Restaurant;
import com.rtechnologies.feastdive.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //Authenticate Restaurant
    //Get restaurants within the radius according to the user location
    public RestaurantResponse createRestaurant(Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByEmail(restaurant.getEmail());
        if(!existingRestaurant.isPresent()) {
            restaurant.setPassword(passwordEncoder.encode(restaurant.getPassword()));
            restaurantRepository.save(restaurant);
            return mapToRestaurantResponse(restaurant);
        } else{
            throw new RuntimeException("Account already exists");
        }
    }

    public RestaurantResponse updateRestaurant(String email, Restaurant updatedRestaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByEmail(email);
        if(existingRestaurant.isPresent()) {
            updatedRestaurant.setId(existingRestaurant.get().getId());
            updatedRestaurant.setPassword(existingRestaurant.get().getPassword());
            restaurantRepository.save(updatedRestaurant);
            return mapToRestaurantResponse(updatedRestaurant);
        }
        throw new NotFoundException("Restaurant not found");
    }

    public RestaurantResponse getRestaurant(String email) throws NotFoundException {
        Optional<Restaurant> restaurant = restaurantRepository.findByEmail(email);
        if(!restaurant.isPresent()) {
            throw new NotFoundException("Restaurant not found");
        }
        return mapToRestaurantResponse(restaurant.get());
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        Optional<Restaurant> restaurant = restaurantRepository.findByEmail(email);
        if (!restaurant.isPresent()) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, restaurant.get().getPassword())) {
            throw new RuntimeException("Incorrect old password");
        }

        restaurant.get().setPassword(passwordEncoder.encode(newPassword));
        restaurantRepository.save(restaurant.get());
    }

    private RestaurantResponse mapToRestaurantResponse(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .rating(restaurant.getRating())
                .contact(restaurant.getContact())
                .address(restaurant.getAddress())
                .imgURL(restaurant.getImgURL())
                .isOpen(restaurant.isOpen())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .dineIn(restaurant.isDineIn())
                .takeAway(restaurant.isTakeAway())
                .build();
    }
}
