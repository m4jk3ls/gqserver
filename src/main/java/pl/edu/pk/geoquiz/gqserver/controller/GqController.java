package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.geoquiz.gqserver.api.RestCountries;
import pl.edu.pk.geoquiz.gqserver.model.AnswerRequest;
import pl.edu.pk.geoquiz.gqserver.model.AnswerResponse;
import pl.edu.pk.geoquiz.gqserver.model.QuestionResponse;
import pl.edu.pk.geoquiz.gqserver.model.entity.ActiveQuestion;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionAttributes;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionsView;
import pl.edu.pk.geoquiz.gqserver.repository.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/geoquiz")
public class GqController {

	private ActiveQuestionRepository activeQRepo;
	private ArchivesRepository archivesRepo;
	private QuestionAttributesRepository qAttribRepo;
	private QuestionsViewRepository qViewRepo;

	@GetMapping(path = "/question")
	public QuestionResponse getQuestion() {

		List<BigDecimal> allQuestionIds = qViewRepo.findAllIds();
		Integer questionId = 10;//allQuestionIds.get(new Random().nextInt(allQuestionIds.size())).toBigInteger().intValueExact();
		Optional<QuestionsView> questionViewOptional = qViewRepo.findById(questionId);
		if (!questionViewOptional.isPresent()) {
			// Tutaj powinno chyba byc rzucenie wyjatku
			return null;
		}
		QuestionsView questionView = questionViewOptional.get();
		String qAbout = questionView.getQuestionAbout();

		RestCountries restCountries = new RestCountries();
		List<Map<String, String>> countryAndAnswer = restCountries.getCountriesWithSpecificField(qAbout, 4);

		String token = getToken();
		int index = new Random().nextInt(countryAndAnswer.size());
		String country = countryAndAnswer.get(index).get("name");
		String fullQuestion = questionView.getContent().replaceFirst("<\\?>", questionView.getAttribute()).replaceFirst("<\\?>", country);
		String answer = null;
		List<String> possibleAnswers = new ArrayList<>();
		for (int i = 0; i < countryAndAnswer.size(); i++) {
			if (qAbout.contains("|") && i == index) {
				answer = countryAndAnswer.get(i).get(qAbout.substring(0, qAbout.indexOf("|")));
				possibleAnswers.add(answer);
			} else if (qAbout.contains("|") && i != index) {
				possibleAnswers.add(countryAndAnswer.get(i).get(qAbout.substring(0, qAbout.indexOf("|"))));
			} else if (!qAbout.contains("|") && i == index) {
				answer = countryAndAnswer.get(i).get(qAbout);
				possibleAnswers.add(answer);
			} else {
				possibleAnswers.add(countryAndAnswer.get(i).get(qAbout));
			}
		}
		String answerHash = Integer.toString(Objects.requireNonNull(answer).hashCode());

		ActiveQuestion aQuestion = new ActiveQuestion(token, answerHash, new Timestamp(new Date().getTime()));
		Optional<QuestionAttributes> qAttribOptional = qAttribRepo.findById(questionView.getId());
		if (!qAttribOptional.isPresent()) {
			// Tutaj powinno chyba byc rzucenie wyjatku
			return null;
		}
		aQuestion.setQuestionAttributes(qAttribOptional.get());
		activeQRepo.save(aQuestion);

		return new QuestionResponse(token, fullQuestion, possibleAnswers, country);
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

	private String getToken() {
		String chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder tokenBuilder = new StringBuilder(ActiveQuestion.getTokenLength());
		for (int i = 0; i < ActiveQuestion.getTokenLength(); i++) {
			tokenBuilder.append(chars.charAt(new Random().nextInt(chars.length())));
		}
		return tokenBuilder.toString();
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
	public void setqAttribRepo(QuestionAttributesRepository qAttribRepo) {
		this.qAttribRepo = qAttribRepo;
	}

	@Autowired
	public void setQViewRepo(QuestionsViewRepository qViewRepo) {
		this.qViewRepo = qViewRepo;
	}
}