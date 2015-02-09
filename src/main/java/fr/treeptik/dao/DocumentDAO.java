package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Document;
import fr.treeptik.model.Site;

public interface DocumentDAO extends JpaRepository<Document, Integer> {

	
	@Query("select d from Client c join c.documents d where c.login = :userLogin")
	List<Document> findByClientLogin(@Param("userLogin") String userLogin)
			throws DataAccessException;
}
