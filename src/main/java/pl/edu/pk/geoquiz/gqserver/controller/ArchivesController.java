package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.geoquiz.gqserver.model.Archives;
import pl.edu.pk.geoquiz.gqserver.repository.ArchivesRepository;

import java.util.Optional;

@RestController
public class ArchivesController {

	private ArchivesRepository archivesRepository;

	@PostMapping(path = "/geoquiz/archives")
	public void createNewArchives() {
		Optional<Archives> archivesOptional = archivesRepository.findById(1);
		Archives archives = archivesOptional.get();
		archives.setBadAnswers(1);
		archives.setGoodAnswers(5);
		archivesRepository.save(archives);
	}

	@Autowired
	public void setArchivesRepository(ArchivesRepository archivesRepository) {
		this.archivesRepository = archivesRepository;
	}
}
