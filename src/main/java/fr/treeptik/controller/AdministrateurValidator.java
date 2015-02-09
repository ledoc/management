package fr.treeptik.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.treeptik.dao.AdministrateurDAO;
import fr.treeptik.dao.ClientDAO;
import fr.treeptik.model.Administrateur;

@Component
public class AdministrateurValidator implements Validator {

	@Inject
	private AdministrateurDAO adminDAO;
	
	@Inject
	private ClientDAO clientDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object obj, Errors errors) {

		if (obj instanceof Administrateur) {
			Administrateur a = (Administrateur) obj;
			
			
			if (a.getId()==null){
				Long countByLogin = adminDAO.countByLogin(a.getLogin());
				
				if(countByLogin != 0){
					errors.rejectValue("login", "hack", "Login déjà utilisé");
				}
			} else {
				Long countByLogin = adminDAO.countByLoginAndID(a.getLogin(), a.getId());
				
				if(countByLogin != 0){
					errors.rejectValue("login", "hack", "Login déjà utilisé");
				}
			}
			
			Long countByLoginClient = clientDAO.countByLogin(a.getLogin());
			if(countByLoginClient != 0){
				errors.rejectValue("login", "hack", "Login déjà utilisé");
			}
			
			
			
		}
		
	}

}
