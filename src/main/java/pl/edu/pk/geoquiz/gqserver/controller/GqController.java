package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.geoquiz.gqserver.api.RestCountries;
import pl.edu.pk.geoquiz.gqserver.exception.NoQuestionsInDatabaseException;
import pl.edu.pk.geoquiz.gqserver.exception.QuestionExpiredException;
import pl.edu.pk.geoquiz.gqserver.model.AnswerRequest;
import pl.edu.pk.geoquiz.gqserver.model.AnswerResponse;
import pl.edu.pk.geoquiz.gqserver.model.QuestionResponse;
import pl.edu.pk.geoquiz.gqserver.model.entity.ActiveQuestion;
import pl.edu.pk.geoquiz.gqserver.model.entity.Archives;
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

	@GetMapping(path = "/question/{token}")
	public Map<String, String> getStats(@PathVariable String token) {

		Map<String, String> stats = new HashMap<>();
		Optional<ActiveQuestion> activeQuestionOptional = activeQRepo.findById(token);
		if (!activeQuestionOptional.isPresent()) {
			stats.put("goodAnswers", "-");
			stats.put("badAnswers", "-");
		} else {
			ActiveQuestion activeQuestion = activeQuestionOptional.get();
			Optional<Archives> archivesOptional = archivesRepo.findByQAttribId(activeQuestion.getQuestionAttributes().getId());
			if (!archivesOptional.isPresent()) {
				stats.put("goodAnswers", "-");
				stats.put("badAnswers", "-");
			} else {
				Archives archives = archivesOptional.get();
				stats.put("goodAnswers", Integer.toString(archives.getGoodAnswers()));
				stats.put("badAnswers", Integer.toString(archives.getBadAnswers()));
			}
		}
		return stats;
	}

	@GetMapping(path = "/question")
	public QuestionResponse getQuestion() {

		List<BigDecimal> allQuestionIds = qViewRepo.findAllIds();
		if (allQuestionIds.isEmpty()) {
			throw new NoQuestionsInDatabaseException("There are no questions in database. Try again later...");
		}
		Integer questionId = allQuestionIds.get(new Random().nextInt(allQuestionIds.size())).toBigInteger().intValueExact();
		Optional<QuestionsView> questionViewOptional = qViewRepo.findById(questionId);
		if (!questionViewOptional.isPresent()) {
			return null;
		}
		QuestionsView questionView = questionViewOptional.get();
		String qAbout = questionView.getQuestionAbout();

		RestCountries restCountries = new RestCountries();
		List<Map<String, String>> countryAndAnswer = restCountries.getCountriesWithSpecificField(qAbout, 4);

		String token = getToken();
		int index = new Random().nextInt(countryAndAnswer.size());
		String country = countryAndAnswer.get(index).get("name");
		String alpha2Code = countryAndAnswer.get(index).get("alpha2Code");
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

		if (qAttribOptional.isPresent()) {
			QuestionAttributes aAttrib = qAttribOptional.get();
			aQuestion.setQuestionAttributes(aAttrib);
			activeQRepo.save(aQuestion);
			Optional<Archives> archivesOptional = archivesRepo.findByQAttribId(aQuestion.getQuestionAttributes().getId());
			if (archivesOptional.isPresent()) {
				Archives archives = archivesOptional.get();
				return new QuestionResponse(token, fullQuestion, possibleAnswers, country, alpha2Code,
						Integer.toString(archives.getGoodAnswers()), Integer.toString(archives.getBadAnswers()));
			}
			return new QuestionResponse(token, fullQuestion, possibleAnswers, country, alpha2Code, "-", "-");
		}
		return null;
	}

	@PostMapping(path = "/question/answer")
	public AnswerResponse postAnswer(@RequestBody AnswerRequest answerRequest) {

		Optional<ActiveQuestion> activeQuestionOptional = activeQRepo.findById(answerRequest.getToken());
		if (!activeQuestionOptional.isPresent()) {
			throw new QuestionExpiredException("Question expired...");
		}
		ActiveQuestion activeQuestion = activeQuestionOptional.get();
		Optional<Archives> archivesOptional = archivesRepo.findByQAttribId(activeQuestion.getQuestionAttributes().getId());
		AnswerResponse answerResponse = new AnswerResponse();

		if (Integer.toString(answerRequest.getUserAnswer().hashCode()).equals(activeQuestion.getAnswer())) {
			if (archivesOptional.isPresent()) {
				Archives archives = archivesOptional.get();
				archives.setGoodAnswers(archives.getGoodAnswers() + 1);
				archivesRepo.save(archives);
			}
			answerResponse.setCorrect(true);
			answerResponse.setCorrectAnswer(answerRequest.getUserAnswer());
		} else {
			if (archivesOptional.isPresent()) {
				Archives archives = archivesOptional.get();
				archives.setBadAnswers(archives.getBadAnswers() + 1);
				archivesRepo.save(archives);
			}
			answerResponse.setCorrect(false);
			List<String> possibleAnswers = answerRequest.getPossibleAnswers();
			for (String curr : possibleAnswers) {
				if (Integer.toString(curr.hashCode()).equals(activeQuestion.getAnswer())) {
					answerResponse.setCorrectAnswer(curr);
				}
			}
		}
		activeQRepo.delete(activeQuestion);
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