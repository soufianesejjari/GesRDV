package com.sejjari.gesrdv;

import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.Creneau;
import com.sejjari.gesrdv.data.entete.RendezVous;
import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.repository.CentreSanteRepository;
import com.sejjari.gesrdv.data.repository.CreneauRepository;
import com.sejjari.gesrdv.data.repository.RendezVousRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import com.sejjari.gesrdv.data.services.CentreSanteService;
import com.sejjari.gesrdv.data.services.CreneauService;
import com.sejjari.gesrdv.data.services.RendezVousService;
import com.sejjari.gesrdv.data.services.UtilisateurService;
import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class GesRdvApplication {





    public static void main(String[] args) {
        SpringApplication.run(GesRdvApplication.class, args);
    }
}
