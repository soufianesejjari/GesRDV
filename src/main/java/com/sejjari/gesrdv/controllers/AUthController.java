package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.dto.UtilisateurDto;
import com.sejjari.gesrdv.data.entete.Role;
import com.sejjari.gesrdv.data.entete.Utilisateur;
import com.sejjari.gesrdv.data.repository.RoleRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import com.sejjari.gesrdv.data.services.UtilisateurService;
import com.sejjari.gesrdv.securite.JWTgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")

public class AUthController {
    private AuthenticationManager authenticationManager;
    private UtilisateurRepository utilisateurRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTgenerator jwTgenerator;
    private UtilisateurService utilisateurService;

    public AUthController(AuthenticationManager authenticationManager, UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTgenerator jwTgenerator, UtilisateurService utilisateurService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwTgenerator = jwTgenerator;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping(path = "inscreption")
    public ResponseEntity <String> inscreption ( @RequestBody UtilisateurDto utilisateurDto){
        if(utilisateurRepository.existsByEmail(utilisateurDto.getEmail())){
            return new ResponseEntity<>("Email  exist dans la base donnes , essayer de connecte par mot de passe  ",HttpStatus.BAD_REQUEST);
        }
        Utilisateur utilisateur=new Utilisateur();
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse()));

        utilisateurService.createUtilisateur(utilisateur);

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateurDto.getEmail(),utilisateurDto.getMotDePasse())

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwTgenerator.generateToken(authentication);
        return new ResponseEntity<>(token,HttpStatus.CREATED);

    }

    @PostMapping(path = "connexion")
    public ResponseEntity <String> connexion ( @RequestBody UtilisateurDto utilisateurDto){

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateurDto.getEmail(),utilisateurDto.getMotDePasse())

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwTgenerator.generateToken(authentication);
        return new ResponseEntity<>(token,HttpStatus.OK);





    }

}

