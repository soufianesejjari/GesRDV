package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.dto.RenderVousDto;
import com.sejjari.gesrdv.data.entity.CentreSante;
import com.sejjari.gesrdv.data.entity.Creneau;
import com.sejjari.gesrdv.data.entity.RendezVous;
import com.sejjari.gesrdv.data.entity.Utilisateur;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import com.sejjari.gesrdv.data.repository.RendezVousRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final CentreSanteRepository centreSanteRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CreneauRepository creneauRepository;


    public RendezVousService(RendezVousRepository rendezVousRepository, CentreSanteRepository centreSanteRepository, UtilisateurRepository utilisateurRepository, CreneauRepository creneauRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.centreSanteRepository = centreSanteRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.creneauRepository = creneauRepository;
    }

    public RendezVous createRendezVous(RenderVousDto rendezVous) {
        // Récupérer le centre de santé correspondant à partir de l'ID fourni
        Optional<CentreSante> centreSanteOptional = centreSanteRepository.findById(rendezVous.getCentreSante_Id());
        CentreSante centreSante = centreSanteOptional.orElseThrow(() -> new IllegalArgumentException("Centre de santé non trouvé"));

        // Récupérer l'utilisateur correspondant à partir de l'ID fourni
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Utilisateur> utilisateurOptional;

        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
             utilisateurOptional = utilisateurRepository.findById(rendezVous.getUtilisateur_Id());

        }
        else {
             utilisateurOptional = utilisateurRepository.findByEmail(userDetails.getUsername());

        }
        Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Récupérer le Creneau
   //     Optional<Creneau> creneauOptional = creneauRepository.findById(rendezVous.getCrenea_Id());
     //   Creneau creneau = creneauOptional.orElseThrow(() -> new IllegalArgumentException("Creneau non trouvé"));

        // Récupérer le Crenau pour le centre
        Optional<Creneau> creneauWithCentreOptional = creneauRepository.findByIdAndCentresSanteId(rendezVous.getCrenea_Id(),rendezVous.getCentreSante_Id());
        Creneau creneauWithCentre = creneauWithCentreOptional.orElseThrow(() -> new IllegalArgumentException(" Pas de Creneau disponible pour ce centre  "));


        if(avalaibleCapacitys(rendezVous.getCentreSante_Id(), rendezVous.getDateRendezVous(),creneauWithCentre)>0){
            // Créer l'objet de rendez-vous avec les données fournies
            RendezVous createdRendezVous = new RendezVous();
            createdRendezVous.setCentreSante(centreSante);
            createdRendezVous.setUtilisateur(utilisateur);
            createdRendezVous.setCreneau(creneauWithCentre);
            createdRendezVous.setDateRendezVous(rendezVous.getDateRendezVous());
            createdRendezVous.setStatut(rendezVous.getEtat());
            System.out.println("rrrrrrrrrrrrrrr ");
            // Enregistrer le rendez-vous dans la base de données et le retourner
            return rendezVousRepository.save(createdRendezVous);
        }
        else {
            throw new IllegalArgumentException("Créneau non disponible pour cette date");
        }

    }


    public Iterable <RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public RendezVous getRendezVousById(Long id) {
        Optional<RendezVous> rendezVousOptional = rendezVousRepository.findById(id);
        return rendezVousOptional.orElse(null);
    }

    public RendezVous updateRendezVous(Long id, RendezVous rendezVous) {
        Optional<RendezVous> existingRendezVousOptional = rendezVousRepository.findById(id);
        if (existingRendezVousOptional.isPresent()) {
            rendezVous.setId(id);
            return rendezVousRepository.save(rendezVous);
        } else {
            return null;
        }
    }
    public List<Creneau> searchAvailableRendezVous(Long centreId, Date date, String creneauStartDate) {
        List<Creneau> availableRendezVous = new ArrayList<Creneau>();

        if (creneauStartDate != null) { // Case 1: If creneauId is provided
            try {
                Optional<List<Creneau>> creneau = creneauRepository.findByHeureDebutAndDate(creneauStartDate, date);

                creneau.ifPresent(crnList -> {
                    crnList.forEach(crn -> {
                        System.out.println("papapapappapapapa");
                        int avalaiblecapacity=avalaibleCapacitys(centreId, date, crn);

                        if (avalaiblecapacity>0) {
                            crn.setCapacite(avalaiblecapacity);
                            availableRendezVous.add(crn);
                        }
                    });
                });
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace(); // or log the exception
            }


        } else { // Case 2: If creneauId is not provided
            Optional<List<Creneau>> creneaux = creneauRepository.findByCentresSanteIdAndDate(centreId,date);
            System.out.println("ddddddddddddddddd "+creneaux.stream().toList().size());
            creneaux.ifPresent(crns -> {
                for (Creneau creneau : crns) {
                    System.out.println("daaaaaaaaaaaaaaate "+date.toString());
                    int avalaiblecapacity=avalaibleCapacitys(centreId, date, creneau);
                    if (avalaiblecapacity>0) {
                        System.out.println("11111111111111111111111111111111111111111");
                        creneau.setCapacite(avalaiblecapacity);
                            availableRendezVous.add(creneau );


                    }
                }
            });
        }

        return availableRendezVous;
    }

    private int avalaibleCapacitys(Long centreId, Date date, Creneau creneau) {
        int creneauCapacity =creneau.getCapacite();
        int bookedRendezVousCount = rendezVousRepository.countByCentreSanteIdAndDateRendezVousAndCreneauId(centreId, date, creneau.getId());
        int availableCapacity = creneauCapacity - bookedRendezVousCount;
        System.out.println("les infos:: "+bookedRendezVousCount+" creno "+ creneauCapacity);
        return availableCapacity ;
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }
}