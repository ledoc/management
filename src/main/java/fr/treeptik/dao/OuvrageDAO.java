package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Ouvrage;

public interface OuvrageDAO extends JpaRepository<Ouvrage, Integer> {

	@Query("select o from Ouvrage o left join fetch o.mesures where o.id = :id")
	public Ouvrage findByIdWithJoinFetchMesures(@Param("id") Integer id) throws DataAccessException;

	@Query("select o from Ouvrage o left join fetch o.documents where o.id = :id")
	public Ouvrage findByIdWithJoinFetchDocuments(@Param("id") Integer id) throws DataAccessException;

	@Query("select o from Ouvrage o left join fetch o.enregistreurs where o.id = :id")
	public Ouvrage findByIdWithJoinFetchEnregistreurs(@Param("id") Integer id) throws DataAccessException;

}
