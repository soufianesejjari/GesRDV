package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.dto.CnetreSanteDto;
import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.Creneau;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CentreSanteService {

    private CentreSanteRepository centreSanteRepository;
    private CreneauRepository creneauRepository;
    public CentreSante createCentreSante(CentreSante centreSante) {
        return centreSanteRepository.save(centreSante);
    }
    public CentreSanteService(CentreSanteRepository centreSanteRepository, CreneauRepository creneauRepository) {
        this.centreSanteRepository = centreSanteRepository;
        this.creneauRepository = creneauRepository;
    }

    public <CentreSante> Iterable<CentreSante> getAllCentreSante() {
        return (Iterable<CentreSante>) centreSanteRepository.findAll();
    }

    public CentreSante getCentreSanteById(Long id) {
        Optional<CentreSante> centreSanteOptional = centreSanteRepository.findById(id);
        return centreSanteOptional.orElse(null);
    }

    public CentreSante updateCentreSante(Long id, CentreSante centreSante) {
        Optional<CentreSante> existingCentreSanteOptional = centreSanteRepository.findById(id);
        if (existingCentreSanteOptional.isPresent()) {
            centreSante.setId(id);
            return centreSanteRepository.save(centreSante);
        } else {
            return null;
        }
    }

    public void deleteCentreSante(Long id) {
        centreSanteRepository.deleteById(id);
    }

    public CentreSante createCentreSanteWithCreneaux(CnetreSanteDto request) {
        CentreSante centreSante = new CentreSante();
        // Définissez les propriétés du centre de santé à partir de la requête
        centreSante.setNom(request.getNom());
        centreSante.setAdresse(request.getAdresse());
        centreSante.setTelephone(request.getTelephone());
        centreSante.setEmail(request.getEmail());
        centreSante.setSiteWeb(request.getSiteWeb());



        // Récupérez les créneaux correspondants à partir de leurs IDs
        Iterable<Creneau> creneaux = creneauRepository.findAllById(request.getCreneauIds());

        centreSante.setCreneaux((List<Creneau>) creneaux);
        // Enregistrez d'abord le centre de santé pour obtenir son ID
        CentreSante savedCentreSante = centreSanteRepository.save(centreSante);


        // Associez chaque créneau au centre de santé
        for (Creneau creneau : creneaux) {
            //System.out.println("test de creneau "+creneau.toString());

            creneau.getCentresSante().add(savedCentreSante);
        }


        // Enregistrez les modifications apportées aux créneaux
        creneauRepository.saveAll(creneaux);

        // Retournez le centre de santé avec les créneaux associés
        return savedCentreSante;
    }
}