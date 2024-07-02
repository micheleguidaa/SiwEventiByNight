package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.repository.LocalRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

	@Autowired
	protected LocalRepository localRepository;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private OwnerService ownerService;

	private static final String UPLOADED_FOLDER = "uploads/locals/";
	private static final String DEFAULT_IMAGE = "/images/default/senzaFotoLocal.jpeg";

	@Transactional
	public Local getLocal(Long id) {
		Optional<Local> result = this.localRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Local saveLocal(Local local,Long ownerId, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			local.setUrlImage(DEFAULT_IMAGE);
		} else {
			String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
			local.setUrlImage(fileUrl);
		}
		local.setOwner(ownerService.getOwner(ownerId));
		return this.localRepository.save(local);
	}
	
	// per proprietario
	@Transactional
	public Local updateLocal(Long id, Local localDetails, MultipartFile file) throws IOException {
		Local local = this.getLocal(id);
		if (local != null) {
			local.setName(localDetails.getName());
			local.setAddress(localDetails.getAddress());
			if (!file.isEmpty()) {
				String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
				local.setUrlImage(fileUrl);
			}
			return this.localRepository.save(local);
		}
		return null;
	}
	
	//per admin
	@Transactional
	public Local updateLocal(Long id, Local localDetails, Long ownerId, MultipartFile file) throws IOException {
		Local local = this.getLocal(id);
		if (local != null) {
			local.setName(localDetails.getName());
			local.setAddress(localDetails.getAddress());
			local.setOwner(ownerService.getOwner(ownerId));
			if (!file.isEmpty()) {
				String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
				local.setUrlImage(fileUrl);
			}
			return this.localRepository.save(local);
		}
		return null;
	}

	@Transactional
	public List<Local> getAllLocals() {
		return (List<Local>) localRepository.findAll();
	}

	@Transactional
	public void deleteById(Long id) {
		Optional<Local> local = localRepository.findById(id);
		local.ifPresent(l -> {
			try {
				fileService.deleteFile(l.getUrlImage(), UPLOADED_FOLDER);
				localRepository.deleteById(id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Transactional
	public List<Local> findAllSortedByName() {
		return localRepository.findAllByOrderByNameAsc();
	}

	public List<Local> findByName(String stringa) {
		return localRepository.findByNameStartingWithIgnoreCase(stringa);
	}


}
