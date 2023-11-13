package com.rtechnologies.feastdive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant extends User{
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
