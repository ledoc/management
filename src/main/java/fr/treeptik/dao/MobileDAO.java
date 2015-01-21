package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.deveryware.Mobile;

public interface MobileDAO extends JpaRepository<Mobile, Integer> {

	Mobile findByMid(String mid) throws DataAccessException;

	@Query("select m from Mobile m join fetch m.trameDWs where m.mid = :mid")
	public Mobile findByMidWithJoinFechTrameDWs(@Param("mid") String mid) throws DataAccessException;
}
