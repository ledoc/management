package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.AlerteEmise;

public interface AlerteEmiseDAO extends JpaRepository<AlerteEmise, Integer> {

	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.capteurs c2 join c2.alerteEmises a where c.login = :userLogin")
	public List<AlerteEmise> findAllByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;

	@Query("select a from AlerteEmise a where a.acquittement = FALSE")
	public List<AlerteEmise> findAllNonAcquittees() throws DataAccessException;

	@Query("select a from Capteur c join c.alerteEmises a where c.id = :capteurId")
	public List<AlerteEmise> findAlertesActivesByCapteurId(
			@Param("capteurId") Integer capteurId)
			throws DataAccessException;

	@Query("select a from AlerteEmise a where a.codeAlerte = :codeAlerte  and a.date=(select max(a.date) from a) ")
	public AlerteEmise findLastAlerteEmiseByCodeAlerte(
			@Param("codeAlerte") String codeAlerte) throws DataAccessException;

}
