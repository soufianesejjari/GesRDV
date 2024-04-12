package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entete.Creneau;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreneauRepository extends CrudRepository<Creneau,Long> {
}
