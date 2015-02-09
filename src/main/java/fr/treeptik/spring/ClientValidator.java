package fr.treeptik.spring;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.treeptik.dao.AdministrateurDAO;
import fr.treeptik.dao.ClientDAO;
import fr.treeptik.model.Client;

@Component
public class ClientValidator implements Validator {

	@Inject
	private ClientDAO clientDAO;
	
	@Inject
	private AdministrateurDAO adminDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object obj, Errors errors) {

		if (obj instanceof Client) {
			Client c = (Client) obj;
			
			
			if (c.getId()==null){
				Long countByLogin = clientDAO.countByLogin(c.getLogin());
				
				if(countByLogin != 0){
					errors.rejectValue("login", "hack", "Login déjà utilisé");
				}
			} else {
				Long countByLogin = clientDAO.countByLoginAndID(c.getLogin(), c.getId());
				
				if(countByLogin != 0){
					errors.rejectValue("login", "hack", "Login déjà utilisé");
				}
			}
			
			Long countByLogin = adminDAO.countByLogin(c.getLogin());
			
			if(countByLogin != 0){
				errors.rejectValue("login", "hack", "Login déjà utilisé");
			}
			
		}
		
	}

}
