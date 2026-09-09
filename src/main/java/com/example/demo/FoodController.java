package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/events")
public class FoodController {

    private final FoodEventService service;

    public FoodController(FoodEventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FoodEvents>> getEvents(
            @RequestParam(defaultValue = "all") String filter) {

        List<FoodEvents> events = switch (filter) {
            case "confirmed" -> service.getConfirmed();
            case "potential" -> service.getPotential();
            case "today"     -> service.getToday();
            default          -> service.getAllUpcoming();
        };

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodEvents> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<FoodEvents> create(@Valid @RequestBody FoodEvents event) {
        FoodEvents created = service.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodEvents> update(
            @PathVariable Long id,
            @Valid @RequestBody FoodEvents event) {
        return ResponseEntity.ok(service.update(id, event));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/report")
    public ResponseEntity<FoodEvents> addReport(@PathVariable Long id) {
        return ResponseEntity.ok(service.addReport(id));
    }

}