package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.RendezVous;
import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public RendezVous createRendezVous(Utilisateur utilisateur, CentreSante centreSante, Date dateRendezVous, String statut) {
        return RendezVous.builder()
                .utilisateur(utilisateur)
                .centreSante(centreSante)
                .dateRendezVous(dateRendezVous)
                .statut(statut)
                .build();
    }

    public RendezVous save(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    // Autres méthodes du service...
}
