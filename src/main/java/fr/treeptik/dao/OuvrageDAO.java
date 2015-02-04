package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Ouvrage;

public interface OuvrageDAO extends JpaRepository<Ouvrage, Integer> {

	@Query("select o from Ouvrage o where o not in (select o2 from Site s join s.ouvrages o2)")
	public List<Ouvrage> findFreeOuvrages()throws DataAccessException;
	
	@Query("select o from Ouvrage o left join fetch o.mesures where o.id = :id")
	public Ouvrage findByIdWithJoinFetchMesures(@Param("id") Integer id) throws DataAccessException;

	@Query("select o from Ouvrage o left join fetch o.documents where o.id = :id")
	public Ouvrage findByIdWithJoinFetchDocuments(@Param("id") Integer id) throws DataAccessException;

	@Query("select o from Ouvrage o left join fetch o.enregistreurs where o.id = :id")
	public Ouvrage findByIdWithJoinFetchEnregistreurs(@Param("id") Integer id) throws DataAccessException;

	@Query("select o from Client c join c.etablissements e join e.sites s join s.ouvrages o where c.login = :userLogin")
	public List<Ouvrage> findByClientLogin(@Param("userLogin") String userLogin) throws DataAccessException;
	
	@Query("select o from Client c join c.etablissements e join e.sites s join s.ouvrages o where c.id = :userId")
	public List<Ouvrage> findByClientId(@Param("userId") Integer userId) throws DataAccessException;
	
}
