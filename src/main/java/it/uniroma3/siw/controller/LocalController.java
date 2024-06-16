package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    
	@GetMapping("/local/{id}")
	public String getLocal(@PathVariable("id") Long id, Model model) {
		model.addAttribute("local", localService.findById(id));
		return "local";
	}
	
	// Mostra la pagina con l'elenco di tutti gli users per l'amministratore
	@GetMapping("/admin/locals")
	public String showAdminLocals(Model model) {
		model.addAttribute("locals", localService.findAll());
		return "Admin/indexLocalsAdmin";
	}
	
	@PostMapping("/admin/delete/local/{id}")
	public String deleteLocal(@PathVariable("id") Long id) {
		localService.deleteById(id);
		return "redirect:/admin/locals";
	}

}
