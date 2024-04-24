package com.sejjari.gesrdv.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {
    private String prenom;
    private String nom;
    private String email;
    private String password;
    private String telephone;
}
