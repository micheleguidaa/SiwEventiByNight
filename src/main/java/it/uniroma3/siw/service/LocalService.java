package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.repository.LocalRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

	@Autowired
	protected LocalRepository localRepository;

	@Autowired
	private FileService fileService;

	private static final String UPLOADED_FOLDER = "uploads/locals/";
	private static final String DEFAULT_IMAGE = "/images/default/senzaFoto.jpeg";

	@Transactional
	public Local getLocal(Long id) {
		Optional<Local> result = this.localRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Local saveLocal(Local local, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			local.setUrlImage(DEFAULT_IMAGE);
		} else {
			String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
			local.setUrlImage(fileUrl);
		}
		return this.localRepository.save(local);
	}

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
		List<Local> locals = new ArrayList<>();
		Iterable<Local> iterable = this.localRepository.findAll();
		for (Local local : iterable) {
			locals.add(local);
		}
		locals.sort((local1, local2) -> local1.getName().compareToIgnoreCase(local2.getName()));
		return locals;
	}

	public List<Local> findByName(String stringa) {
		return localRepository.findByNameStartingWithIgnoreCase(stringa);
	}


}
