package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Etablissement;

public interface EtablissementDAO extends JpaRepository<Etablissement, Integer> {

	@Query("select e from Etablissement e left join fetch e.sites where e.id = :id")
	public Etablissement findByIdWithJoinFetchSites(@Param("id") Integer id) throws DataAccessException;

	@Query("select e from Client c join c.etablissements e Where c.login= :login")
	public List<Etablissement> findByClient(@Param("login") String login);
}
