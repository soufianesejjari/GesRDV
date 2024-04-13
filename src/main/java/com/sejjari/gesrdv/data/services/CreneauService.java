package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.Creneau;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CreneauService {

        private final CreneauRepository creneauRepository;
        private final CentreSanteRepository centreSanteRepository;



    public CreneauService(CreneauRepository creneauRepository, CentreSanteRepository centreSanteRepository) {
        this.creneauRepository = creneauRepository;
        this.centreSanteRepository = centreSanteRepository;
    }

    public Creneau createCreneau(Creneau creneau) {
            return creneauRepository.save(creneau);
        }

        public Iterable<Creneau> getAllCreneaux() {
            return (Iterable<Creneau>) creneauRepository.findAll();
        }

        public Creneau getCreneauById(Long id) {
            Optional<Creneau> creneauOptional = creneauRepository.findById(id);
            return creneauOptional.orElse(null);
        }

        public Creneau updateCreneau(Long id, Creneau creneau) {
            Optional<Creneau> existingCreneauOptional = creneauRepository.findById(id);
            if (existingCreneauOptional.isPresent()) {
                creneau.setId(id);
                return creneauRepository.save(creneau);
            } else {
                return null;
            }
        }

        public void deleteCreneau(Long id) {
            creneauRepository.deleteById(id);
        }

    public CentreSante createCentreForCreneau(Long creneauId, CentreSante centreSante) {
        Optional<Creneau> optionalCreneau = creneauRepository.findById(creneauId);
        if (optionalCreneau.isPresent()) {
            Creneau creneau = optionalCreneau.get();
            Iterable<CentreSante> centresSante = creneau.getCentresSante();
            centresSante.forEach(centre -> centre.getCreneaux().add(creneau));
            creneau.setCentresSante((Set<CentreSante>) centresSante);
            return centreSanteRepository.save(centreSante);
        } else {
            // Gérer le cas où le créneau n'existe pas
            return null;
        }
    }

    }
