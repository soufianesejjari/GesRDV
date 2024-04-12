package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CentreSanteService {

    @Autowired
    private CentreSanteRepository centreSanteRepository;

    public CentreSante createCentreSante(String nom, String adresse, String telephone, String email, String siteWeb) {
        return CentreSante.builder()
                .nom(nom)
                .adresse(adresse)
                .telephone(telephone)
                .email(email)
                .siteWeb(siteWeb)
                .build();
    }

    public CentreSante save(CentreSante centreSante) {
        return centreSanteRepository.save(centreSante);
    }

    // Autres méthodes du service...
}
