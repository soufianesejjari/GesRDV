package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.dto.RenderVousDto;
import com.sejjari.gesrdv.data.entete.RendezVous;
import com.sejjari.gesrdv.data.services.RendezVousService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping
    public ResponseEntity<RendezVous> createRendezVous(@RequestBody RenderVousDto rendezVousdto) {
        RendezVous createdRendezVous = rendezVousService.createRendezVous(rendezVousdto);
        return new ResponseEntity<>(createdRendezVous, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        RendezVous rendezVous = rendezVousService.getRendezVousById(id);
        if (rendezVous != null) {
            return new ResponseEntity<>(rendezVous, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<RendezVous>> getAllRendezVous() {
        Iterable<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
        return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVous> updateRendezVous(@PathVariable Long id, @RequestBody RendezVous rendezVous) {
        RendezVous updatedRendezVous = rendezVousService.updateRendezVous(id, rendezVous);
        if (updatedRendezVous != null) {
            return new ResponseEntity<>(updatedRendezVous, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}