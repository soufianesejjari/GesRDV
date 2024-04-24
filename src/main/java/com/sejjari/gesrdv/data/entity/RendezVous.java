package com.sejjari.gesrdv.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "centre_sante_id")
    private CentreSante centreSante;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Creneau_id")
    private Creneau creneau;
    @Temporal(TemporalType.DATE)
    private Date dateRendezVous;
    private String statut;

    // Getters et setters
}