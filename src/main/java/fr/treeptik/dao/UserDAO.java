package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {

}
