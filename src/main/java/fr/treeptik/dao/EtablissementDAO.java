package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Etablissement;

public interface EtablissementDAO extends JpaRepository<Etablissement, Integer> {

}
