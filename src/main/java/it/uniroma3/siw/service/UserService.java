package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UserService {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private CredentialsService credentialsService;

	private static final String UPLOADED_FOLDER = "uploads/utenti2/";
	private static final String DEFAULT_IMAGE = "/images/default/senzaFotoUser.jpeg";

	/**
	 * This method retrieves a User from the DB based on its ID.
	 * 
	 * @param id the id of the User to retrieve from the DB
	 * @return the retrieved User, or null if no User with the passed ID could be
	 *         found in the DB
	 */
	@Transactional
	public User getUser(Long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}

	/**
	 * This method saves a User in the DB.
	 * 
	 * @param user the User to save into the DB
	 * @return the saved User
	 * @throws DataIntegrityViolationException if a User with the same username as
	 *                                         the passed User already exists in the
	 *                                         DB
	 */
	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	/**
	 * This method retrieves all Users from the DB.
	 * 
	 * @return a List with all the retrieved Users
	 */
	@Transactional
	public List<User> getAllUsers() {
		List<User> result = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		for (User user : iterable)
			result.add(user);
		return result;
	}

	@Transactional
	public void registerUser(User user, Credentials credentials, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			user.setUrlImage(DEFAULT_IMAGE);
		} else {
			String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
			user.setUrlImage(fileUrl);
		}
		credentials.setUser(user);
		credentials.setRole(Credentials.DEFAULT_ROLE);

		saveUser(user);
		credentialsService.saveCredentials(credentials);
	}

	@Transactional
	public void deleteById(Long id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(c -> {
			try {
				fileService.deleteFile(c.getUrlImage(), UPLOADED_FOLDER);
				userRepository.deleteById(id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
