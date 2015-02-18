package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Alerte;

public interface AlerteDAO extends JpaRepository<Alerte, Integer> {


	@Query("select count(a) from Alerte a where a.activation is true and a.emise is false")
	public Long countAlertesActives() throws DataAccessException;
	
	@Query("select count(a) from Alerte a where a.emise is false")
	public Long countAllAlertes() throws DataAccessException;

	@Query("select a from Alerte a where a.emise is true")
	public List<Alerte> findAllAlertesEmises()
			throws DataAccessException;
	
	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.alertes a where c.id = :userLogin and a.activation is true  and a.emise is false")
	public List<Alerte> findByClientLogin(@Param("userLogin") String userLogin)
			throws DataAccessException;

	@Query("select count(a) from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.alertes a where c.id = :userLogin  and a.emise is false")
	public Long countAllAlertesByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;

	@Query("select count(a) from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.alertes a where c.id = :userLogin and a.activation is true  and a.emise is false")
	public Long countAlertesActivesByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;
	
	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.alertes a where c.id = :userLogin and a.emise is true")
	public List<Alerte> findAlertesEmisesByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;
	
	@Query("select a from Enregistreur e join e.alertes a where e.id = :enregistreurId and a.activation is true  and a.emise is false")
	public List<Alerte> findAlertesActivesByEnregistreurId(
			@Param("enregistreurId") Integer enregistreurId) throws DataAccessException;

}
