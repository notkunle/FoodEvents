package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.*;
@Repository
public interface foodEventRepository extends JpaRepository<FoodEvents, Long> {
}
