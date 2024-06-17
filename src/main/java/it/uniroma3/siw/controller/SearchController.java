package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.service.EventService;
import it.uniroma3.siw.service.LocalService;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.model.Event;



@Controller
public class SearchController {
	
	@Autowired
	private LocalService localService;
	
    @Autowired
    private EventService eventService;
	
    @PostMapping("/search")
    public String search(@RequestParam String stringa, RedirectAttributes redirectAttributes) {
        if (stringa.length() == 0) {
            redirectAttributes.addFlashAttribute("events", this.eventService.findAll());
            redirectAttributes.addFlashAttribute("locals", this.localService.getAllLocals());
        } else {
            List<Event> eventiTrovati = this.eventService.findByName(stringa);
            redirectAttributes.addFlashAttribute("events", eventiTrovati);
            
            List<Local> localiTrovati = this.localService.findByName(stringa);
            redirectAttributes.addFlashAttribute("locals", localiTrovati);
        }
        return "redirect:/founds";
    }

    @GetMapping("/founds")
    public String found(Model model) {
        return "Founds";
    }
    
    @PostMapping("/admin/search")
    public String searchAdmin(@RequestParam String stringa, RedirectAttributes redirectAttributes) {
        if (stringa.length() == 0) {
            redirectAttributes.addFlashAttribute("events", this.eventService.findAll());
            redirectAttributes.addFlashAttribute("locals", this.localService.getAllLocals());
        } else {
            List<Event> eventiTrovati = this.eventService.findByName(stringa);
            redirectAttributes.addFlashAttribute("events", eventiTrovati);
            
            List<Local> localiTrovati = this.localService.findByName(stringa);
            redirectAttributes.addFlashAttribute("locals", localiTrovati);
        }
        return "redirect:/admin/founds";
    }

    @GetMapping("/admin/founds")
    public String foundCuochiAdmin(Model model) {
        return "admin/FoundsAdmin";
    }
    

}
