package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.geoquiz.gqserver.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/geoquiz")
public class GqController {

	private ActiveQuestionRepository activeQRepo;
	private ArchivesRepository archivesRepo;
	private QuestionsViewRepository qViewRepo;

	@GetMapping(path="/question")
	public void getQuestion(){
		List<BigDecimal> ids = qViewRepo.findAllIds();
		Random rand = new Random();
		BigDecimal randomId = ids.get(rand.nextInt(ids.size()));
		System.out.println(randomId);
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
	public void setQViewRepo(QuestionsViewRepository qViewRepo) {
		this.qViewRepo = qViewRepo;
	}
}