package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.dto.CnetreSanteDto;
import com.sejjari.gesrdv.data.entity.CentreSante;
import com.sejjari.gesrdv.data.services.CentreSanteService;
import com.sejjari.gesrdv.data.services.CreneauService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/centres")
public class CentreController {




    private final CentreSanteService centreSanteService;
    private final CreneauService creneauService ;
    public CentreController(CentreSanteService centreSanteService, CreneauService creneauService) {
        this.centreSanteService = centreSanteService;
        this.creneauService = creneauService;
    }

    @PostMapping
    public ResponseEntity<CentreSante> createCentreSante(@RequestBody CentreSante centreSante) {
        CentreSante createdCentreSante = centreSanteService.createCentreSante(centreSante);
        return new ResponseEntity<>(createdCentreSante, HttpStatus.CREATED);
    }


    @PostMapping("/avecCreneau")
    public ResponseEntity<CentreSante> createCentreSanteWithCreneaux(@RequestBody CnetreSanteDto centreSante) {
       System.out.println("haaaaaaaaaaaaa"+ centreSante.toString());
        CentreSante createdCentreSante = centreSanteService.createCentreSanteWithCreneaux(centreSante);
        return new ResponseEntity<>(createdCentreSante, HttpStatus.CREATED);
    }




    @PostMapping("/creneau/{creneauId}")
    public ResponseEntity<CentreSante> createCentreSantePourCreneau(@RequestBody CentreSante centreSante,@PathVariable(name = "creneauId") Long creneauId) {
        CentreSante createdCentre = creneauService.createCentreForCreneau(creneauId, centreSante);
        return new ResponseEntity<>(createdCentre, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentreSante> getCentreSanteById(@PathVariable Long id) {
        CentreSante centreSante = centreSanteService.getCentreSanteById(id);
        if (centreSante != null) {
            return new ResponseEntity<>(centreSante, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<CentreSante>> getAllCentreSante() {
        Iterable<CentreSante> centreSantes = centreSanteService.getAllCentreSante();
        return new ResponseEntity<>(centreSantes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentreSante> updateCentreSante(@PathVariable Long id, @RequestBody CentreSante centreSante) {
        CentreSante updatedCentreSante = centreSanteService.updateCentreSante(id, centreSante);
        if (updatedCentreSante != null) {
            return new ResponseEntity<>(updatedCentreSante, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentreSante(@PathVariable Long id) {
        centreSanteService.deleteCentreSante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}