package com.example.demo;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FoodEventService {
    private FoodEventRepository repo;


    public List<FoodEvents> getAllUpcoming(){
        return repo.findByEventTimeAfterOrderByEventTimeAsc(LocalDateTime.now());

    }

    // Only confirmed (5+ reports)
    public List<FoodEvents> getConfirmed() {
        return repo.findConfirmedEvents(LocalDateTime.now());
    }

    public List<FoodEvents> getPotential() {
        return repo.findPotentialEvents(LocalDateTime.now());
    }

    // Events happening today
    public List<FoodEvents> getToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusSeconds(1);
        return repo.findTodayEvents(start, end);
    }

    public FoodEvents getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }





}
