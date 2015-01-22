package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Etablissement;

public interface EtablissementDAO extends JpaRepository<Etablissement, Integer> {

	@Query("select e from Etablissement e left join fetch e.sites where e.id = :id")
	public Etablissement findByIdWithJoinFechSites(@Param("id") Integer id) throws DataAccessException;
}
