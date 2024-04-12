package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.Creneau;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreneauService {

    @Autowired
    private CreneauRepository creneauRepository;

    public Creneau createCreneau(String jour, String heureDebut, String heureFin, int capacite) {
        return Creneau.builder()
                .jour(jour)
                .heureDebut(heureDebut)
                .heureFin(heureFin)
                .capacite(capacite)
                .build();
    }

    public Creneau save(Creneau creneau) {
        return creneauRepository.save(creneau);
    }

    // Autres méthodes du service...
}
