package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Enregistreur;

public interface EnregistreurDAO extends JpaRepository<Enregistreur, Integer> {

	Enregistreur findByMid(String mid) throws DataAccessException;

	@Query("select m from Enregistreur m left join fetch m.trameDWs where m.mid = :mid")
	public Enregistreur findByMidWithJoinFechTrameDWs(@Param("mid") String mid) throws DataAccessException;
}
