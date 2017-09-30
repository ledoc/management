package fr.treeptik.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.RepasDate;

public interface RepasDateDAO extends
		JpaRepository<RepasDate, Long> {

	@Query("select distinct r.date from RepasDate r")
	List<Date> listAllDatesOfRepas() throws DataAccessException;

	@Query("select distinct rd from RepasDate rd left join fetch rd.repas r left join fetch r.listPlats")
	List<RepasDate> findAllWithListPlat() throws DataAccessException;

	@Query("select rd from RepasDate rd left join fetch rd.repas r left join fetch r.listPlats where rd.id=:id")
	RepasDate findByIdWithListPlat(@Param("id") Long id) throws DataAccessException;
}
