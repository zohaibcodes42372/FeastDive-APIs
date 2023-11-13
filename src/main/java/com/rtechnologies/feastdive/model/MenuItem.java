package com.rtechnologies.feastdive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"menuItemName"})})
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long restaurantId;
    private long categoryId;
    private String menuItemName;
    private String description;
    private double rating;
    private double price;
}
