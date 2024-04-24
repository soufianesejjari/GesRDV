package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entity.RendezVous;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Repository
public interface RendezVousRepository extends CrudRepository<RendezVous,Long> {
     Collection<? extends RendezVous> findByCentreSanteIdAndDateRendezVousAndCreneauId(Long centreSanteId, Timestamp date, Long creneauId);

    int countByCentreSanteIdAndDateRendezVousAndCreneauId(Long centreSanteId, Date date, Long creneauId);


    Long countBydateRendezVousAfter(Date today);

    Long countByDateRendezVous(LocalDate now);
}
