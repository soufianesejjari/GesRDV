package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")

public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Endpoint to create a new utilisateur
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur createdUtilisateur = utilisateurService.createUtilisateur(utilisateur.getPrenom(),
                utilisateur.getNom(),utilisateur.getEmail(),utilisateur.getMotDePasse()
                ,utilisateur.getTelephone(),utilisateur.getRole()

                );

        return new ResponseEntity<>(createdUtilisateur, HttpStatus.CREATED);
    }

    // endpoint to retrieve all users
    @GetMapping
    public ResponseEntity<Iterable<Utilisateur>> getUtilisateurs() {
        Iterable<Utilisateur> all_utilisateurs = utilisateurService.getAllUtilisateurs();

        return new ResponseEntity<>(all_utilisateurs, HttpStatus.OK);
    }



    // Endpoint to retrieve a specific utilisateur by id
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update an existing utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        if (updatedUtilisateur != null) {
            return new ResponseEntity<>(updatedUtilisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete an utilisateur by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
