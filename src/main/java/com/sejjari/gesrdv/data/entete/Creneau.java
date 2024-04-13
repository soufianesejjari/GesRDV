package com.sejjari.gesrdv.data.entete;




import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "creneaux")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jour;
    private String heureDebut;
    private String heureFin;
    private int capacite;

    @JsonIgnore

    @ManyToMany(mappedBy = "creneaux",fetch = FetchType.LAZY)
    private Set<CentreSante> centresSante;

    // Getters et setters
}
