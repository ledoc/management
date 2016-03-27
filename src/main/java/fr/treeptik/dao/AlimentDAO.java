package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Aliment;

public interface AlimentDAO extends
		JpaRepository<Aliment, Long> {


}
