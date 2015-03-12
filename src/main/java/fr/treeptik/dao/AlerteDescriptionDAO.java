package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.AlerteDescription;

public interface AlerteDescriptionDAO extends
		JpaRepository<AlerteDescription, Integer> {

	@Query("select count(a) from AlerteDescription a where a.activation is true")
	public Long countAllActives() throws DataAccessException;

	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.capteurs c2 join c2.alerteDescriptions a where c.login = :userLogin")
	public List<AlerteDescription> findAllByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;
	
	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.capteurs c2 join c2.alerteDescriptions a where c.login = :userLogin and a.activation is true")
	public List<AlerteDescription> findAllAlertesActivesByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;

	@Query("select count(a) from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.capteurs c2 join c2.alerteDescriptions a where c.login = :userLogin")
	public Long countAllByClientLogin(@Param("userLogin") String userLogin)
			throws DataAccessException;

	@Query("select count(a) from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.capteurs c2 join c2.alerteDescriptions a where c.login = :userLogin and a.activation is true")
	public Long countAllActivesByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;

	@Query("select a from Capteur c join c.alerteDescriptions a where c.id = :capteurId and a.activation is true")
	public List<AlerteDescription> findAlertesActivesByCapteurId(
			@Param("capteurId") Integer capteurId)
			throws DataAccessException;

}
