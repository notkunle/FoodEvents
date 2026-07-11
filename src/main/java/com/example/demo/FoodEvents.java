package com.example.demo;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_event")
@Data
@NoArgsConstructor

public class FoodEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //name of the event or club
    @NotBlank(message = "Event name is required")
    @Column(nullable = false)
    private String name;

    // the location
    @NotBlank(message = "location is required")
    @Column(nullable = false)
    private String location;

    // the room
    private String room;


    // time for the event
    @NotNull(message = "Event time is required")
    @Column(nullable = false)
    private LocalDateTime eventTime;

    //food type
    @NotBlank(message = "Food type is required")
    @Column(nullable = false)
    private String foodType;

    //optional notes
    @Column(columnDefinition = "TEXT")
    private String notes;


    // How many people have reported/confirmed this event.
    @Column(nullable = false)
    private int reportCount = 1;

    // Times when the post was created
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Transient
    public String getStatus(){
        if (reportCount >= 5){
            return "Confirmed";
        } else{
            return "Potential";
        }
    }
}
