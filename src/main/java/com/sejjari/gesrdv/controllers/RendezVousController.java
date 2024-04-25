package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.dto.RenderVousDto;
import com.sejjari.gesrdv.data.entity.Creneau;
import com.sejjari.gesrdv.data.entity.RendezVous;
import com.sejjari.gesrdv.data.services.RendezVousService;
import com.sejjari.gesrdv.securite.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {
    private final RendezVousService rendezVousService;
    private final EmailService emailService;


    public RendezVousController(RendezVousService rendezVousService, EmailService emailService) {
        this.rendezVousService = rendezVousService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> createRendezVous(@RequestBody RenderVousDto rendezVousdto) {
        try {
            RendezVous createdRendezVous = rendezVousService.createRendezVous(rendezVousdto);
            String subject = "Confirmation de votre rendez-vous";
            String email = createdRendezVous.getUtilisateur().getEmail();
            String prenom = createdRendezVous.getUtilisateur().getPrenom();
            String rendezVousDate = createdRendezVous.getDateRendezVous().toString(); // Assuming Date is the field representing the rendezvous date
            String rendezVousStartTime = createdRendezVous.getCreneau().getHeureDebut().toString(); // Assuming startTime is the field representing the start time of the rendezvous
            String rendezVousId = createdRendezVous.getId().toString(); // Assuming Id is the field representing the rendezvous ID
            String centreNomComplet = createdRendezVous.getCentreSante().getNom(); // Assuming nomComplet is the field representing the full name of the center
            String centreAdresse = createdRendezVous.getCentreSante().getAdresse(); // Assuming adresse is the field representing the address of the center

            String template = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Rendez-vous Confirmation</title>\n" +
                    "</head>\n" +
                    "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: #007bff;\">Confirmation de votre rendez-vous</h1>\n" +
                    "    <p>Bonjour " + prenom + ",</p>\n" +
                    "    <p>Votre rendez-vous a été confirmé avec succès.</p>\n" +
                    "    <p>Voici les détails de votre rendez-vous :</p>\n" +
                    "    <ul>\n" +
                    "        <li>Date du rendez-vous : " + rendezVousDate + "</li>\n" +
                    "        <li>Heure de début : " + rendezVousStartTime + "</li>\n" +
                    "        <li>ID du rendez-vous : " + rendezVousId + "</li>\n" +
                    "        <li>Nom du centre : " + centreNomComplet + "</li>\n" +
                    "        <li>Adresse du centre : " + centreAdresse + "</li>\n" +
                    "    </ul>\n" +
                    "    <p>Merci de votre confiance.</p>\n" +
                    "</body>\n" +
                    "</html>";

            emailService.sendEmail(email, subject, template);

            return new ResponseEntity<>(createdRendezVous, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error dans ", e.getMessage()));        } catch (
                MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchAvailableRendezVous(
            @RequestParam("centre") Long centreId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "creneau", required = false) String creneauStartDate
    ) {
        try {
            System.out.println("id de rendezvous " + centreId);
            // Call the service method to search for available rendezvous
            List<Creneau> availableRendezVous = rendezVousService.searchAvailableRendezVous(centreId, date, creneauStartDate);

            // Check if any available rendezvous were found
            if (!availableRendezVous.isEmpty()) {
                return new ResponseEntity<>(availableRendezVous, HttpStatus.OK);
            } else {
                // No available rendezvous found for the given criteria
                return new ResponseEntity<>("Pas des creneau disponible pour cette date", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate response entity with the exception message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(e.getMessage()));
        }
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