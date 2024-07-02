package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.repository.LocalRepository;

@Component
public class LocalValidator implements Validator  {
	@Autowired
	private LocalRepository localRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Local.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Local local = (Local) target;
		if (local.getName() != null && this.localRepository.existsByName(local.getName())) {
			errors.reject("local.duplicate");
		}

	}
}
