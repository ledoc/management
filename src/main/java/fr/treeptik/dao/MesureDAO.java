package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Mesure;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {

	@Query("select m from Enregistreur e join e.mesures m where e.id = :id")
	List<Mesure> findByEnregistreurId(@Param("id") Integer id)
			throws DataAccessException;
}
