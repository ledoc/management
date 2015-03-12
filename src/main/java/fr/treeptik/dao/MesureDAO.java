package fr.treeptik.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Mesure;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {

	@Query("select m from Mesure m join m.capteur c join c.mesures")
	List<Mesure> findAllDetails() throws DataAccessException;

	@Query("select distinct m from Mesure m left join fetch m.capteur c left join fetch c.mesures m2 join m2.capteur c2 where m.id = :id")
	Mesure findByIdWithFetch(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select distinct m from Capteur c join c.mesures m left join fetch m.capteur c2 join c2.mesures where c.id = :id")
	List<Mesure> findByCapteurIdWithFetch(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select distinct m from Capteur c join c.mesures m left join fetch m.capteur c2 join c2.mesures where m.date BETWEEN :dateDebut AND :dateFin and c.id = :id ")
	List<Mesure> findByCapteurIdBetweenDates(@Param("id") Integer id , @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin)
			throws DataAccessException;
}
