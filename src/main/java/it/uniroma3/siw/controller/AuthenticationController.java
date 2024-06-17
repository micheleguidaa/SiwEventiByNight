package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;

   
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
    }

    @GetMapping("/register")
	public String formNewCuoco(HttpServletRequest request, HttpSession session, Model model) {
	    String referer = request.getHeader("Referer");
	    if (referer != null) {
	        session.setAttribute("prevPage", referer);
	    }
	    model.addAttribute("user", new User());
	    model.addAttribute("credentials", new Credentials());
	    return "formRegisterUser";
	}
	

	@PostMapping("/register")
	public String registerCuoco(@Valid @ModelAttribute("user") User user,
	                            BindingResult cuocoBindingResult, 
	                            @Valid @ModelAttribute("credentials") Credentials credentials,
	                            BindingResult credenzialiBindingResult,
	                            @RequestParam("fileImage") MultipartFile file,
	                            HttpSession session,
	                            Model model) {
	    // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
	    if (!cuocoBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
	        try {
	        	userService.registerUser(user, credentials, file);
	        } catch (IOException e) {
	            e.printStackTrace();
	            model.addAttribute("fileUploadError", "Errore nel caricamento dell'immagine");
	            return "formRegister";
	        }
	        model.addAttribute("user", user);
	        
	        // Reindirizza alla pagina precedente
	        String prevPage = (String) session.getAttribute("prevPage");
	        if (prevPage != null) {
	            session.removeAttribute("prevPage"); // Rimuovi l'URL dalla sessione
	            return "redirect:" + prevPage;
	        }
	        return "login";
	    }
	    return "formRegisterUser";
	}
	
	

}