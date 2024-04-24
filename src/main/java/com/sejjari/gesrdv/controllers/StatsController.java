package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import com.sejjari.gesrdv.data.repository.RendezVousRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/stats")

public class StatsController {
    private final UtilisateurRepository utilisateurRepository;
    private final CentreSanteRepository centreSanteRepository;
    private final RendezVousRepository rendezVousRepository;
    private final CreneauRepository creneauRepository;
    public StatsController(UtilisateurRepository utilisateurRepository, CentreSanteRepository centreSanteRepository, RendezVousRepository rendezVousRepository, CreneauRepository creneauRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.centreSanteRepository = centreSanteRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.creneauRepository = creneauRepository;
    }

    @GetMapping("")
    public ResponseEntity<HashMap<String, String>> getStats() {
        HashMap<String, String> stats = new HashMap<>();

        Long countUtilisateurs = utilisateurRepository.count();
        Long countCentres = centreSanteRepository.count();
        Long countRdv = rendezVousRepository.count();
        Long countCreneau = creneauRepository.count();

        // Date d'aujourd'hui
        LocalDate today = LocalDate.now();

        // Nombre de rendez-vous aujourd'hui
        Long countRdvAujourdhui = rendezVousRepository.countByDateRendezVous(today);

        // Nombre de rendez-vous après aujourd'hui
        Date todayDate = new Date();
        Long countRdvApresAujourdHui = rendezVousRepository.countBydateRendezVousAfter(todayDate);

        // Date maximale dans la table creneau
        LocalDate dateMaxCreneau = creneauRepository.findMaxDate();

        // Ajouter les statistiques à la HashMap
        stats.put("countUtilisateurs", countUtilisateurs.toString());
        stats.put("countCentres", countCentres.toString());
        stats.put("countRdv", countRdv.toString());
        stats.put("countCreneau", countCreneau.toString());
        stats.put("countRdvAujourdhui", countRdvAujourdhui.toString());
        stats.put("countRdvApresAujourdHui", countRdvApresAujourdHui.toString());
        stats.put("dateMaxCreneau", dateMaxCreneau.toString());

        return ResponseEntity.ok(stats);
    }
}
