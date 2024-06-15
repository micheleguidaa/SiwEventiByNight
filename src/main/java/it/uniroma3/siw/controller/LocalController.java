package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.LocalService;

public class LocalController {
	@Autowired
    private LocalService localService;

    
    // Visualizza tutte le ricette
    @GetMapping("/locals")
    public String showLocals(Model model) {
        model.addAttribute("ricette", localService.findAll());
        return "locals"; 
    }
}
