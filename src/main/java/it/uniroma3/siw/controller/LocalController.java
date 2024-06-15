package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.LocalService;

@Controller
public class LocalController {
	@Autowired
    private LocalService localService;

    // Visualizza tutti locali
    @GetMapping("/locals")
    public String showLocals(Model model) {
        model.addAttribute("locals", localService.findAll());
        return "locals"; 
    }
}
