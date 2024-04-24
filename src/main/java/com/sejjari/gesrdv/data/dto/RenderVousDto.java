package com.sejjari.gesrdv.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RenderVousDto {
    private Long utilisateur_Id;

    private Long centreSante_Id;
    private Long crenea_Id;
    private Date dateRendezVous;
    private String etat ;
}
