package fr.treeptik.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Finance;

public interface FinanceDAO extends JpaRepository<Finance, Integer> {

	@Query("select f from Finance f  where f.date BETWEEN :dateDebut AND :dateFin")
	List<Finance> findBetweenDates(@Param("dateDebut") Date dateDebut,
			@Param("dateFin") Date dateFin) throws DataAccessException;

	@Query("select f from Finance f  where categorie=:categorie")
	List<Finance> listAllByCategorie(@Param("categorie") String categorie) throws DataAccessException;
	
	@Query("select distinct f.categorie from Finance f")
	List<String> listAllCategories() throws DataAccessException;
	
	@Query("select sum(f.montant) from Finance f where f.categorie=:categorie and f.revenu=false")
	Double sumByCategorie(@Param("categorie") String categorie) throws DataAccessException;
	
	@Query("select f.total from Finance f where f.id=(select max(f.id) from f)")
	Double selectLastTotal() throws DataAccessException;
}
