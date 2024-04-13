package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {

        return save(utilisateur);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }


    // Autres méthodes du service...
    // Retrieve all utilisateurs
    public Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Retrieve an utilisateur by ID
    public Utilisateur getUtilisateurById(Long id) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        return utilisateurOptional.orElse(null);
    }

    // Update an existing utilisateur
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur) {
        // Check if utilisateur with the given ID exists
        Optional<Utilisateur> existingUtilisateurOptional = utilisateurRepository.findById(id);
        if (existingUtilisateurOptional.isPresent()) {
            // Set the ID of the provided utilisateur to match the existing utilisateur
            utilisateur.setId(id);
            // Additional validation or processing logic can be added here if needed
            return utilisateurRepository.save(utilisateur);
        } else {
            return null; // Utilisateur with the given ID not found
        }
    }

    // Delete an utilisateur by ID
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}

