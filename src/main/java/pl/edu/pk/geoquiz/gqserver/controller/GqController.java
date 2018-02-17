package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.geoquiz.gqserver.model.AnswerRequest;
import pl.edu.pk.geoquiz.gqserver.model.AnswerResponse;
import pl.edu.pk.geoquiz.gqserver.model.QuestionResponse;
import pl.edu.pk.geoquiz.gqserver.model.entity.ActiveQuestion;
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

		/*// Select random question
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

		return new QuestionResponse(token, fullQuestion, possibleAnswers, countryToShow);*/

		List<BigDecimal> allQuestionIds = qViewRepo.findAllIds();
		Integer questionId = allQuestionIds.get(new Random().nextInt(allQuestionIds.size())).toBigInteger().intValueExact();
		Optional<QuestionsView> questionAttribOptional = qViewRepo.findById(questionId);
		if (!questionAttribOptional.isPresent()) {
			return null;
		}
		QuestionsView questionAttrib = questionAttribOptional.get();
		return null;
	}

	@PostMapping(path = "/question/answer")
	public AnswerResponse postAnswer(@RequestBody AnswerRequest answerRequest) {

		Optional<ActiveQuestion> activeQuestionOptional = activeQRepo.findById(answerRequest.getToken());
		if (!activeQuestionOptional.isPresent()) {
			// Tutaj powinno chyba byc rzucenie wyjatku
			return null;
		}

		AnswerResponse answerResponse = new AnswerResponse();
		ActiveQuestion activeQuestion = activeQuestionOptional.get();

		if (Integer.toString(answerRequest.getUserAnswer().hashCode()).equals(activeQuestion.getAnswer())) {
			answerResponse.setCorrect(true);
			answerResponse.setCorrectAnswer(answerRequest.getUserAnswer());
		} else {
			answerResponse.setCorrect(false);
			List<String> possibleAnswers = answerRequest.getPossibleAnswers();
			for (String curr : possibleAnswers) {
				if (Integer.toString(curr.hashCode()).equals(activeQuestion.getAnswer())) {
					answerResponse.setCorrectAnswer(curr);
				}
			}
		}

		return answerResponse;
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