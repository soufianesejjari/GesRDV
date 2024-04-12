package com.sejjari.gesrdv.data.entete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
@Table(name = "centres_sante")
public class CentreSante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String siteWeb;

    @ManyToMany
    @JoinTable(
            name = "centre_sante_creneau",
            joinColumns = @JoinColumn(name = "centre_sante_id"),
            inverseJoinColumns = @JoinColumn(name = "creneau_id")
    )
    private Set<Creneau> creneaux;

    // Getters et setters
}