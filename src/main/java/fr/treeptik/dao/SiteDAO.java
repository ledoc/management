package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Site;

public interface SiteDAO extends JpaRepository<Site, Integer> {

	@Query("select s from Site s left join fetch s.ouvrages where s.id = :id")
	Site findByIdWithJoinFetchOuvrages(@Param("id") Integer id) throws DataAccessException;
	
	@Query("select s from Client c join c.etablissements e join e.sites s where c.login = :userLogin")
	List<Site> findByClientLogin(@Param("userLogin") String userLogin);

}
