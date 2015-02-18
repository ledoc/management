package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Enregistreur;

public interface EnregistreurDAO extends JpaRepository<Enregistreur, Integer> {

	Enregistreur findByMid(String mid) throws DataAccessException;

	@Query("select e from Enregistreur e left join fetch e.trameDWs where e.mid = :mid")
	public Enregistreur findByMidWithJoinFechTrameDWs(@Param("mid") String mid)
			throws DataAccessException;

	@Query("select e from Enregistreur e left join fetch e.alertes where e.id = :id")
	public Enregistreur findByIdWithJoinFetchAlertesActives(
			@Param("id") Integer id) throws DataAccessException;

	@Query("select e from Enregistreur e where e not in (select e2 from Ouvrage o join o.enregistreurs e2)")
	public List<Enregistreur> findFreeEnregistreurs()
			throws DataAccessException;;

}
