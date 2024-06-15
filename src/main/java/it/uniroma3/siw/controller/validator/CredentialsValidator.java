package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

public class CredentialsValidator implements Validator {
	@Autowired
	private CredentialsRepository credenzialiRepository;

	@Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials) target;
		if (credentials.getUsername() != null && credentials.getPassword() != null
				&& this.credenzialiRepository.existsByUsername(credentials.getUsername())) {
			errors.reject("credenziali.duplicate");
		}
	
}
}