package com.example.rentIntelliJ;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {
    private static final Map<String, Car> cars = new HashMap<>();
    private static final Map<String, Boolean> rentedCars = new HashMap<>();

    static {
        cars.put("11AA22", new Car("11AA22", "Ferrari", 100));
        cars.put("22BB33", new Car("22BB33", "Lamborghini", 200));
    }

    @GetMapping("/cars/{plateNumber}")
    public ResponseEntity<?> getCar(@PathVariable("plateNumber") String plateNumber) {
        Car car = cars.get(plateNumber);
        if (car == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getCars() {
        return ResponseEntity.status(200).body(cars.values());
    }

    @PutMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> rentOrGetBack(@PathVariable("plateNumber") String plateNumber,
                                                @RequestParam(value = "rent", required = true) boolean rent,
                                                @RequestBody(required = false) Dates date) {
        Car car = cars.get(plateNumber);
        if (car == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }

        if (rent) {
            if (rentedCars.getOrDefault(plateNumber, false)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is already rented");
            }
            rentedCars.put(plateNumber, true);
            return ResponseEntity.ok("Car " + plateNumber + " rented from " + date.getBegin() + " to " + date.getEnd());
        } else {
            if (!rentedCars.getOrDefault(plateNumber, false)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is not currently rented");
            }
            rentedCars.put(plateNumber, false);
            return ResponseEntity.ok("Car " + plateNumber + " returned successfully");
        }
    }
}