package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Capteur;
import fr.treeptik.model.TypeMesureOrTrame;

public interface CapteurDAO extends JpaRepository<Capteur, Integer> {

	@Query("select c from Capteur c left join fetch c.alerteDescriptions where c.id = :id")
	public Capteur findByIdWithJoinFetchAlertesActives(@Param("id") Integer id)
			throws DataAccessException;

	@Query("select c from Enregistreur e join e.capteurs c where c.typeMesureOrTrame = :typeMesureOrTrame and e.id = :enregistreurId")
	Capteur findByEnregistreurIdAndTypeMesureOrTrame(
			@Param("typeMesureOrTrame") TypeMesureOrTrame typeMesureOrTrame,
			@Param("enregistreurId") Integer enregistreurId)
			throws DataAccessException;

	@Query("select c from Enregistreur e join e.capteurs c where e.id = :enregistreurId")
	List<Capteur> findAllByEnregistreurId(
			@Param("enregistreurId") Integer enregistreurId)
			throws DataAccessException;

}
