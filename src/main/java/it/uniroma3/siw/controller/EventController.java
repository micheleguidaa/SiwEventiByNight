package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.service.EventService;

@Controller
public class EventController {
	@Autowired
	private EventService eventService;

	// Mostra la pagina con l'elenco di tutti gli eventi
	@GetMapping("/events")
	public String showEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "events";
	}

	@GetMapping("/event/{id}")
	public String getEvent(@PathVariable("id") Long id, Model model) {
		model.addAttribute("event", eventService.getEvent(id));
		return "event";
	}

	// Mostra la pagina con l'elenco di tutti gli eventi per l'amministratore
	@GetMapping("/admin/events")
	public String showAdminEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "Admin/indexEventsAdmin";
	}

	@PostMapping("/admin/delete/event/{id}")
	public String deleteCuoco(@PathVariable("id") Long id) {
		eventService.deleteById(id);
		return "redirect:/admin/events";
	}

}
