package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entete.RendezVous;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezVousRepository extends CrudRepository<RendezVous,Long> {
}
