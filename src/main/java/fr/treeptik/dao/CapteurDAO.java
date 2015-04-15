package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Capteur;
import fr.treeptik.model.TypeCaptAlerteMesure;

public interface CapteurDAO extends JpaRepository<Capteur, Integer> {

	@Query("select c from Capteur c left join fetch c.alerteDescriptions where c.id = :id")
	public Capteur findByIdWithJoinFetchAlertesActives(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select c from Enregistreur e join e.capteurs c where c.typeCaptAlerteMesure = :typeCaptAlerteMesure and e.id = :enregistreurId")
	Capteur findByEnregistreurIdAndTypeCaptAlerteMesure(
			@Param("typeCaptAlerteMesure") TypeCaptAlerteMesure typeCaptAlerteMesure,
			@Param("enregistreurId") Integer enregistreurId)
			throws DataAccessException;

	@Query("select c from Enregistreur e join e.capteurs c where e.id = :enregistreurId")
	List<Capteur> findAllByEnregistreurId(
			@Param("enregistreurId") Integer enregistreurId)
			throws DataAccessException;

    @Query("select c from Ouvrage o join o.enregistreurs e join e.capteurs c  where o.id = :ouvrageId")
    List<Capteur> findAllByOuvrageId(@Param("ouvrageId") Integer ouvrageId)throws DataAccessException;
}
