package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Client;

public interface ClientDAO extends JpaRepository<Client, Integer> {

	@Query("select c from Client c left join fetch c.etablissements where c.id = :id")
	public Client findByIdWithJoinFetchEtablissements(@Param("id") Integer id) throws DataAccessException;

}
