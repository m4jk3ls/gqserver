package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.geoquiz.gqserver.model.QuestionResponse;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionsView;
import pl.edu.pk.geoquiz.gqserver.repository.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/geoquiz")
public class GqController {

	private ActiveQuestionRepository activeQRepo;
	private ArchivesRepository archivesRepo;
	private QuestionsViewRepository qViewRepo;

	@GetMapping(path = "/question")
	public QuestionResponse getQuestion() {

		// Select random question
		List<BigDecimal> ids = qViewRepo.findAllIds();
		Integer questionId = ids.get(new Random().nextInt(ids.size())).toBigInteger().intValueExact();

		Optional<QuestionsView> questionComponentsOptional = qViewRepo.findById(questionId);
		if (!questionComponentsOptional.isPresent()) {
			// Tutaj powinno chyba byc rzucenie wyjatku
			return null;
		}
		// Full question
		QuestionsView questionComponents = questionComponentsOptional.get();
		String fullQuestion = questionComponents.getContent().replaceFirst("<\\?>", questionComponents.getAttribute()).replaceFirst("<\\?>", "Poland");

		// Token
		String chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder tokenBuilder = new StringBuilder(100);
		for (int i = 0; i < 100; i++) {
			tokenBuilder.append(chars.charAt(new Random().nextInt(chars.length())));
		}
		String token = tokenBuilder.toString();

		// Others
		List<String> possibleAnswers = Arrays.asList("Answer A", "Answer B", "Answer C", "Answer D");
		String countryToShow = "Poland";

		return new QuestionResponse(token, fullQuestion, possibleAnswers, countryToShow);
	}

	@PostMapping(path = "/question/answer")
	public void postAnswer() {
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