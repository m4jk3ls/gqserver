package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.geoquiz.gqserver.repository.ActiveQuestionRepository;
import pl.edu.pk.geoquiz.gqserver.repository.ArchivesRepository;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionAttributesRepository;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionContentRepository;

@RestController
@RequestMapping("/geoquiz")
public class GqController {

	private ActiveQuestionRepository activeQRepo;
	private ArchivesRepository archivesRepo;
	private QuestionAttributesRepository qAttribRepo;
	private QuestionContentRepository qContentRepo;

	@GetMapping(path="/question")
	public void getQuestion(){
	}

	@PostMapping(path="/question/answer")
	public void postAnswer(){
	}

	@Autowired
	public void setActiveQRepo(ActiveQuestionRepository activeQRepo) {
		this.activeQRepo = activeQRepo;
	}

	@Autowired
	public void setArchivesRepo(ArchivesRepository archivesRepo) {
		this.archivesRepo = archivesRepo;
	}

	@Autowired
	public void setQAttribRepo(QuestionAttributesRepository qAttribRepo) {
		this.qAttribRepo = qAttribRepo;
	}

	@Autowired
	public void setQContentRepo(QuestionContentRepository qContentRepo) {
		this.qContentRepo = qContentRepo;
	}
}