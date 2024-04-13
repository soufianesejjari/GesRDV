package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entete.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {

    public Optional<Utilisateur> findByEmail( String email);
    public Boolean existsByEmail(String email);
}
