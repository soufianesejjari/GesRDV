package com.sejjari.gesrdv.controllers;

import com.sejjari.gesrdv.data.dto.UtilisateurDto;
import com.sejjari.gesrdv.data.entity.Role;
import com.sejjari.gesrdv.data.entity.Utilisateur;
import com.sejjari.gesrdv.data.repository.RoleRepository;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import com.sejjari.gesrdv.data.services.UtilisateurService;
import com.sejjari.gesrdv.securite.JWTgenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity <HashMap <String, String>> inscreption ( @RequestBody UtilisateurDto utilisateurDto){
        HashMap <String, String> response = new HashMap<>();

        if(utilisateurRepository.existsByEmail(utilisateurDto.getEmail())){
             response.put("error","Email  exist dans la base donnes , essayer de connecte par mot de passe  ");

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        Utilisateur utilisateur=new Utilisateur();
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDto.getPassword()));

        Role defaultRole = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<>(); // Assuming roles is a Set<Role> in your User class

        if (defaultRole != null) {
            roles.add(defaultRole);
            utilisateur.setRoles( roles);
        } else {
            // Handle case where default role is not found
            Role USERRole = new Role(1L,"USER");
                    roleRepository.save(USERRole);
            roles.add(USERRole);
            utilisateur.setRoles(    roles);


        }
        utilisateurService.createUtilisateur(utilisateur);

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateurDto.getEmail(),utilisateurDto.getPassword())

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwTgenerator.generateToken(authentication);
        response.put("token", token);
        response.put("mail", utilisateur.getEmail());
        response.put("userName", utilisateur.getNom()+" "+utilisateur.getPrenom());
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }


    @PostMapping(path = "connexion")
    public ResponseEntity<HashMap <String, String>> connexion(@RequestBody UtilisateurDto utilisateurDto) {
            HashMap <String, String> response = new HashMap<>();
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateurDto.getEmail(), utilisateurDto.getPassword())
            );

            // Set authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String token = jwTgenerator.generateToken(authentication);
            Utilisateur userConnect=utilisateurRepository.findByEmail(utilisateurDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("user not found"));
            response.put("token", token);
            response.put("mail", userConnect.getEmail());
            response.put("userName", userConnect.getNom()+" "+userConnect.getPrenom());
            response.put("roles", userConnect.getRoles().toString());

            // Return token in response
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

