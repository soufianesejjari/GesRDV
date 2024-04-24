package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.dto.AddedCreneau;
import com.sejjari.gesrdv.data.entity.CentreSante;
import com.sejjari.gesrdv.data.entity.Creneau;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import java.util.*;

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

    public void addDefaultCreneaux(AddedCreneau addedCreneau) {
        CentreSante centreSante = centreSanteRepository.findById(addedCreneau.getCenter_id())
                .orElseThrow(() -> new IllegalArgumentException("Pas de centre trouvé pour ce créneau"));
        // Parcourir chaque date dans la plage spécifiée
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(addedCreneau.getStartDate());
        while (!calendar.getTime().after(addedCreneau.getEndDate())) {
            // Ajouter les créneaux pour chaque jour
            //
             addDefaultCreneauxForDate((Date) calendar.getTime(), centreSante);

            // Passer à la prochaine date
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    private void addDefaultCreneauxForDate(Date date, CentreSante centreSante) {
        // Ajouter les créneaux pour chaque heure
        // Pour samedi, ajouter uniquement les créneaux du matin
        // Pour dimanche, ne pas ajouter de créneaux
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY) {
            // Ajouter uniquement les créneaux du matin (de 8h à 12h)
            for (int hour = 8; hour < 12; hour++) {
                Creneau creneau = creerCreneau(date, hour, centreSante);
                // Enregistrer le créneau dans la base de données
                creneauRepository.save(creneau);
            }
        } else if (dayOfWeek != Calendar.SUNDAY) {
            // Pour les autres jours de la semaine, ajouter les créneaux de 8h à 18h
            for (int hour = 8; hour < 18; hour++) {
                Creneau creneau = creerCreneau(date, hour, centreSante);
                // Enregistrer le créneau dans la base de données
                // Sauvegarder le créneau
                creneauRepository.save(creneau);
                if (centreSante.getCreneaux() == null) {
                    centreSante.setCreneaux((List<Creneau>) new HashSet<>(Collections.singletonList(creneau)));
                } else {
                    centreSante.getCreneaux().add(creneau);
                }
            }
        }
    }

    private Creneau creerCreneau(Date date, int hour, CentreSante centreSante) {
        // Créer un nouveau créneau avec les données fournies
        Creneau creneau = new Creneau();
        creneau.setDate(date);
        creneau.setHeureDebut(hour + ":00");
        creneau.setHeureFin((hour + 1) + ":00");
        creneau.setCapacite(5);
        // Associer le centre de santé au créneau
        if (creneau.getCentresSante() == null) {
            creneau.setCentresSante(new HashSet<>(Collections.singletonList(centreSante)));
        } else {
            creneau.getCentresSante().add(centreSante);
        }


        return creneau;
    }
    }
