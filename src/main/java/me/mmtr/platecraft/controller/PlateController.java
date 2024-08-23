package me.mmtr.platecraft.controller;

import me.mmtr.platecraft.service.PlateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class PlateController {

    private final PlateService PLATE_SERVICE;

    public PlateController(PlateService plateService) {
        this.PLATE_SERVICE = plateService;
    }

    @GetMapping("/api/polish/{prefix}")
    public ResponseEntity<?> getPolishCustomPlate(@PathVariable String prefix) {
        return ResponseEntity.ok(PLATE_SERVICE.getPolishPlates(prefix));
    }

    @GetMapping("/api/polish/{prefix}/{length}")
    public ResponseEntity<?> getPolishCustomPlate(@PathVariable String prefix, @PathVariable int length) {
        return ResponseEntity.ok(PLATE_SERVICE.getPolishPlates(prefix, length));
    }

    @GetMapping("/api/english/{prefix}")
    public ResponseEntity<?> getEnglishCustomPlate(@PathVariable String prefix) {
        return ResponseEntity.ok(PLATE_SERVICE.getEnglishPlates(prefix));
    }


    @GetMapping("/api/english/{prefix}/{length}")
    public ResponseEntity<?> getEnglishCustomPlate(@PathVariable String prefix, @PathVariable int length) {
        return ResponseEntity.ok(PLATE_SERVICE.getEnglishPlates(prefix, length));
    }
}
