package com.sejjari.gesrdv.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CnetreSanteDto {
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String siteWeb;
    private Set<Long> creneauIds;
}
