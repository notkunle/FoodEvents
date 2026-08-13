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

    public FoodEvents create(FoodEvents event) {
        // Always start at 1 report (the person submitting it)
        event.setReportCount(1);
        event.setCreatedAt(LocalDateTime.now());
        return repo.save(event);
    }


    public FoodEvents update(Long id, FoodEvents updated) {
        FoodEvents existing = getById(id);

        existing.setName(updated.getName());
        existing.setLocation(updated.getLocation());
        existing.setRoom(updated.getRoom());
        existing.setEventTime(updated.getEventTime());
        existing.setFoodType(updated.getFoodType());
        existing.setNotes(updated.getNotes());
        // Note: reportCount is NOT updated here use addReport() instead

        return repo.save(existing);
    }

    public void delete(Long id) {
        FoodEvents existing = getById(id); // throws if not found
        repo.delete(existing);
    }
    public FoodEvents addReport(Long id) {
        FoodEvents event = getById(id);
        event.setReportCount(event.getReportCount() + 1);
        return repo.save(event);
    }







}
