package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.repository.EventRepository;

@Component
public class EventValidator implements Validator {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Event event = (Event) target;
		if (event.getName() != null) {
			errors.reject("credentials.duplicate");
		}

	}
}
