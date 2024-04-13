package com.sejjari.gesrdv.data.services;

import com.sejjari.gesrdv.data.dto.RenderVousDto;
import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.RendezVous;
import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.RendezVousRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final CentreSanteRepository centreSanteRepository;
    private final UtilisateurRepository utilisateurRepository;


    public RendezVousService(RendezVousRepository rendezVousRepository, CentreSanteRepository centreSanteRepository, UtilisateurRepository utilisateurRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.centreSanteRepository = centreSanteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public RendezVous createRendezVous(RenderVousDto rendezVous) {
        // Récupérer le centre de santé correspondant à partir de l'ID fourni
        Optional<CentreSante> centreSanteOptional = centreSanteRepository.findById(rendezVous.getCentreSante_Id());
        CentreSante centreSante = centreSanteOptional.orElseThrow(() -> new IllegalArgumentException("Centre de santé non trouvé"));

        // Récupérer l'utilisateur correspondant à partir de l'ID fourni
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(rendezVous.getUtilisateur_Id());
        Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Créer l'objet de rendez-vous avec les données fournies
        RendezVous createdRendezVous = new RendezVous();
        createdRendezVous.setCentreSante(centreSante);
        createdRendezVous.setUtilisateur(utilisateur);
        createdRendezVous.setDateRendezVous(rendezVous.getDateRendezVous());
        createdRendezVous.setStatut(rendezVous.getEtat());

        // Enregistrer le rendez-vous dans la base de données et le retourner
        return rendezVousRepository.save(createdRendezVous);
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

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }
}