package me.mmtr.platecraft.controller;

import me.mmtr.platecraft.record.Plate;
import me.mmtr.platecraft.service.PlateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class PlateController {

    private final PlateService plateService;

    public PlateController(PlateService plateService) {
        this.plateService = plateService;
    }

    @GetMapping("/api/polish/{prefix}")
    public ResponseEntity<?> getPolishCustomPlate(@PathVariable String prefix) {
        return ResponseEntity.ok(new Plate(plateService.getPolishPlate(prefix)));
    }

    @GetMapping("/api/polish/{prefix}/{length}")
    public ResponseEntity<?> getPolishCustomPlate(@PathVariable String prefix, @PathVariable int length) {
        return ResponseEntity.ok(new Plate(plateService.getPolishPlate(prefix, length)));
    }

    @GetMapping("/api/english/{prefix}")
    public ResponseEntity<?> getEnglishCustomPlate(@PathVariable String prefix) {
        return ResponseEntity.ok(new Plate(plateService.getEnglishPlate(prefix)));
    }


    @GetMapping("/api/english/{prefix}/{length}")
    public ResponseEntity<?> getEnglishCustomPlate(@PathVariable String prefix, @PathVariable int length) {
        return ResponseEntity.ok(new Plate(plateService.getEnglishPlate(prefix, length)));
    }
}
