package fr.treeptik.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Mesure;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {

	@Query("select m from Mesure m join m.enregistreur e join e.mesures")
	List<Mesure> findAllDetails() throws DataAccessException;

	@Query("select distinct m from Mesure m left join fetch m.enregistreur e left join fetch e.mesures m2 join m2.enregistreur e2 where m.id = :id")
	Mesure findByIdWithFetch(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select distinct m from Enregistreur e join e.mesures m left join fetch m.enregistreur e2 join e2.mesures where e.id = :id")
	List<Mesure> findByEnregistreurIdWithFetch(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select distinct m from Enregistreur e join e.mesures m left join fetch m.enregistreur e2 join e2.mesures where m.date BETWEEN :dateDebut AND :dateFin and e.id = :id ")
	List<Mesure> findByEnregistreurIdBetweenDates(@Param("id") Integer id , @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin)
			throws DataAccessException;
}
