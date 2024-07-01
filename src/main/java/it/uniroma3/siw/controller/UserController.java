package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	// Mostra la pagina con l'elenco di tutti gli users per l'amministratore
	@GetMapping("/admin/users")
	public String showAdminUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "Admin/indexUsersAdmin";
	}
	
    @PostMapping("/admin/delete/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) throws Exception{
    	userService.deleteById(id);
        return "redirect:/admin/users";
    }


}
