package com.rtechnologies.feastdive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private long id;
    private String restaurantName;
    private double rating;
    private String contact;
    private String address;
    private String imgURL;
    private boolean isOpen;
    private String openTime;
    private String closeTime;
    private boolean dineIn;
    private boolean takeAway;
}
