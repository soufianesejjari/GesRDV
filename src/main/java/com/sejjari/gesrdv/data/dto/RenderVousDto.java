package com.sejjari.gesrdv.data.dto;

import com.sejjari.gesrdv.data.entete.CentreSante;
import com.sejjari.gesrdv.data.entete.Utilisateur;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RenderVousDto {
    private Long utilisateur_Id;


    private Long centreSante_Id;

    private Date dateRendezVous;
    private String etat ;
}
