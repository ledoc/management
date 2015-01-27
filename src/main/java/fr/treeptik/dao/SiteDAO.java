package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.treeptik.model.Site;

public interface SiteDAO extends JpaRepository<Site, Integer> {

	@Query("select s from Site s left join fetch s.ouvrages where s.id = :id")
	Site findByIdWithJoinFetchOuvrages(Integer id) throws DataAccessException;

}
