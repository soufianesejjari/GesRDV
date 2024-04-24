package com.sejjari.gesrdv.data.repository;

import com.sejjari.gesrdv.data.entity.Creneau;
import com.sejjari.gesrdv.data.entity.RendezVous;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * CreneauRepository is an interface that extends CrudRepository.
 * It provides CRUD operations for the Creneau entity.
 * It also provides custom query methods for specific operations.
 */
@Repository
public interface CreneauRepository extends CrudRepository<Creneau,Long> {

    /**
     * Find all Creneau entities by the given CentreSante id.
     * @param centreId the id of the CentreSante
     * @return a list of Creneau entities
     */
    Optional<List<Creneau>> findByCentresSanteId(Long centreId );

    /**
     * Find all Creneau entities by the given start date and date.
     * @param startDate the start date
     * @param date the date
     * @return a list of Creneau entities
     */
    Optional<List<Creneau>> findByHeureDebutAndDate(String startDate,Date date);

    /**
     * Find a Creneau entity by the given id and CentreSante id.
     * @param crenauId the id of the Creneau
     * @param centreSanteId the id of the CentreSante
     * @return a Creneau entity
     */
    Optional<Creneau> findByIdAndCentresSanteId(Long crenauId,Long centreSanteId);

    /**
     * Find all Creneau entities by the given CentreSante id and date.
     * @param centreSanteId the id of the CentreSante
     * @param date the date
     * @return a list of Creneau entities
     */
    Optional<List<Creneau>>  findByCentresSanteIdAndDate(Long centreSanteId,Date date);

    /**
     * Find a Creneau entity by the given start time, end time and CentreSante id.
     * @param heureDebut the start time
     * @param heureFin the end time
     * @param centreId the id of the CentreSante
     * @return a Creneau entity
     */
    Optional<Creneau> findByHeureDebutAndHeureFinAndCentresSanteId(String heureDebut, String heureFin, Long centreId);

    /**
     * Find the maximum date among all Creneau entities.
     * @return the maximum date
     */
    @Query("SELECT MAX(c.date) FROM Creneau c")
    LocalDate findMaxDate();
}