package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;

import java.util.*;
@Repository
public interface foodEventRepository extends JpaRepository<FoodEvents, Long> {
    // SELECT * FROM food_event WHERE event_time >= now ORDER BY event_time ASC
    // Only return future/ongoing events, sorted by soonest first
    List<FoodEvents> findByEventTimeAfterOrderByEventTimeAsc(LocalDateTime now);

    // Returns only CONFIRMED events (reportCount >= 5)
    @Query("SELECT e FROM FoodEvents e WHERE e.reportCount >= 5 AND e.eventTime >= :now ORDER BY e.eventTime ASC")
    List<FoodEvents> findConfirmedEvents(LocalDateTime now);

    // Returns only POTENTIAL events (reportCount < 5)
    @Query("SELECT e FROM FoodEvents e WHERE e.reportCount < 5 AND e.eventTime >= :now ORDER BY e.eventTime ASC")
    List<FoodEvents> findPotentialEvents(LocalDateTime now);

    // Events happening today
    @Query("SELECT e FROM FoodEvents e WHERE e.eventTime BETWEEN :startOfDay AND :endOfDay ORDER BY e.eventTime ASC")
    List<FoodEvents> findTodayEvents(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
