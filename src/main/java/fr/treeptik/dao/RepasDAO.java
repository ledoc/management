package fr.treeptik.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Repas;

public interface RepasDAO extends
		JpaRepository<Repas, Long> {

	@Query("select distinct r.date from Repas r")
	List<Date> listAllDatesOfRepas() throws DataAccessException;

	@Query("select distinct r from Repas r left join fetch r.listPlats")
	List<Repas> findAllWithListPlat() throws DataAccessException;

	@Query("select r from Repas r left join fetch r.listPlats where r.id=:id")
	Repas findByIdWithListPlat(@Param("id") Long id) throws DataAccessException;
}
