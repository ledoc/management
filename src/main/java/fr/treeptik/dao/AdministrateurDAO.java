package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Administrateur;

public interface AdministrateurDAO extends
		JpaRepository<Administrateur, Integer> {

	Long countByLogin(String login);

	@Query("select count(a) from Administrateur a Where a.login = :login and a.id != :id")
	Long countByLoginAndID(@Param("login") String login, @Param("id") Integer id);

	Administrateur findByLogin(String userlogin) throws DataAccessException;
}
