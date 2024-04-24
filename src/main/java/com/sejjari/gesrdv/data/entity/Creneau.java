package com.sejjari.gesrdv.data.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "creneaux")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date; // Ajout de l'attribut date

    private String jour;
    private String heureDebut;
    private String heureFin;
    private int capacite;

    @JsonIgnore

    @ManyToMany(mappedBy = "creneaux",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<CentreSante> centresSante =new HashSet<>();

    // Getters et setters
}
