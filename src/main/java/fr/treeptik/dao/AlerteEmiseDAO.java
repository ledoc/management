package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.AlerteEmise;

public interface AlerteEmiseDAO extends
		JpaRepository<AlerteEmise, Integer> {


	@Query("select a from Client c join c.etablissements e join e.sites s join s.ouvrages o join o.enregistreurs e join e.alerteEmises a where c.login = :userLogin")
	public List<AlerteEmise> findAllByClientLogin(
			@Param("userLogin") String userLogin) throws DataAccessException;

	@Query("select a from Enregistreur e join e.alerteEmises a where e.id = :enregistreurId")
	public List<AlerteEmise> findAlertesActivesByEnregistreurId(
			@Param("enregistreurId") Integer enregistreurId)
			throws DataAccessException;

}
