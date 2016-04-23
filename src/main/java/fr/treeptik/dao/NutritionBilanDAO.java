package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.NutritionBilan;

public interface NutritionBilanDAO extends
		JpaRepository<NutritionBilan, Integer> {


}
