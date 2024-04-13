package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entete.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {
}
