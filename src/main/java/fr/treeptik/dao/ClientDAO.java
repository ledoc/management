package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Client;

public interface ClientDAO extends JpaRepository<Client, Integer> {

	@Query("select c from Client c left join fetch c.etablissements where c.id = :id")
	public Client findByIdWithJoinFetchEtablissements(@Param("id") Integer id) throws DataAccessException;

	@Query("select c from Client c left join fetch c.etablissements e join e.sites s join s.ouvrages o where o.id = :ouvrageId")
	public Client findClientByOuvrageId(@Param("ouvrageId") Integer ouvrageId) throws DataAccessException;

	
	@Query("select c from Client c left join fetch c.documents where c.id = :id")
	public Client findByIdWithJoinFetchDocuments(@Param("id") Integer id) throws DataAccessException;

	public Long countByLogin(String login) throws DataAccessException;

	public Long countByIdentifiant(String identifiant) throws DataAccessException;

	@Query("select count(c) from Client c Where c.login = :login and c.id != :id")
	public Long countByLoginAndID(@Param("login") String login, @Param("id") Integer id) throws DataAccessException;

	Client findByLogin(String userlogin) throws DataAccessException;
	
}
