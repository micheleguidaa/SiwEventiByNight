package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Owner;
import it.uniroma3.siw.repository.OwnerRepository;

@Service
public class OwnerService {
	@Autowired
	protected OwnerRepository ownerRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private CredentialsService credentialsService;

	private static final String UPLOADED_FOLDER = "uploads/owners/";
	private static final String DEFAULT_IMAGE = "/images/default/senzaFotoUser.jpeg";


	@Transactional
	public void registerOwner(Owner owner, Credentials credentials, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			owner.setUrlImage(DEFAULT_IMAGE);
		} else {
			String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
			owner.setUrlImage(fileUrl);
		}
		credentials.setOwner(owner);
		credentials.setRole(Credentials.BUSINESS_ROLE);
		System.out.println("credentials.setRole(Credentials.ADMIN_ROLE);");
		System.out.println(credentials.getRole());
		credentialsService.saveCredentials(credentials);

		saveOwner(owner);
	}
	
	@Transactional
	public Owner saveOwner(Owner owner) {
		return this.ownerRepository.save(owner);
	}

	public Owner getOwner(Long ownerId) {
		return ownerRepository.findById(ownerId).orElse(null);
	}
	
	@Transactional
	public List<Owner> findAllSortedByName() {
		return ownerRepository.findAllByOrderByNameAsc();
	}



}
