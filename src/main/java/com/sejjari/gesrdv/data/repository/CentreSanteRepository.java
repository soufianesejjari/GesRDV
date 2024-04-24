package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entity.CentreSante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentreSanteRepository extends CrudRepository<CentreSante,Long> {
}
