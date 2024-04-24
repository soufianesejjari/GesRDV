package com.sejjari.gesrdv.data.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToMany(
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "centre_sante_creneau",
            joinColumns = @JoinColumn(name = "centre_sante_id"),
            inverseJoinColumns = @JoinColumn(name = "creneau_id")
    )
    private List<Creneau> creneaux;

    // Getters et setters
}