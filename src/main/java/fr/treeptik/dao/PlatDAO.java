package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Plat;

public interface PlatDAO extends
		JpaRepository<Plat, Long> {


}
