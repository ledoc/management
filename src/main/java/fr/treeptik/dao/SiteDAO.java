package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Site;

public interface SiteDAO extends JpaRepository<Site, Integer> {

}
