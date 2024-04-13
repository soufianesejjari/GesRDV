package com.sejjari.gesrdv.controllers;
import com.sejjari.gesrdv.data.entete.Creneau;
import com.sejjari.gesrdv.data.services.CreneauService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/creneaux")
public class CreneauController {

    private final CreneauService creneauService;

    public CreneauController(CreneauService creneauService) {
        this.creneauService = creneauService;
    }

    @PostMapping
    public ResponseEntity<Creneau> createCreneau(@RequestBody Creneau creneau) {
        Creneau createdCreneau = creneauService.createCreneau(creneau);
        return new ResponseEntity<>(createdCreneau, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Creneau> getCreneauById(@PathVariable Long id) {
        Creneau creneau = creneauService.getCreneauById(id);
        if (creneau != null) {
            return new ResponseEntity<>(creneau, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Creneau>> getAllCreneaux() {
        Iterable<Creneau> creneaux = creneauService.getAllCreneaux();
        return new ResponseEntity<>(creneaux, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Creneau> updateCreneau(@PathVariable Long id, @RequestBody Creneau creneau) {
        Creneau updatedCreneau = creneauService.updateCreneau(id, creneau);
        if (updatedCreneau != null) {
            return new ResponseEntity<>(updatedCreneau, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        creneauService.deleteCreneau(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
