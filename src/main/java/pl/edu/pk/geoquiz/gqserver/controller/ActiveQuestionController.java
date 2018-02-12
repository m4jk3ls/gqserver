package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.geoquiz.gqserver.model.ActiveQuestion;
import pl.edu.pk.geoquiz.gqserver.model.QuestionAttributes;
import pl.edu.pk.geoquiz.gqserver.repository.ActiveQuestionRepository;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionAttributesRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@RestController
public class ActiveQuestionController {

	private QuestionAttributesRepository qAttributesRepository;
	private ActiveQuestionRepository activeQRepository;

	@PostMapping(path = "/geoquiz/aQuestion")
	public void createNewActiveQuestion() {
		ActiveQuestion aQuestion = new ActiveQuestion("token", "answer", new Timestamp(new Date().getTime()));

		Optional<QuestionAttributes> qAttributesOptional = qAttributesRepository.findById(1);
		QuestionAttributes qAttributes = qAttributesOptional.get();

		aQuestion.setQuestionAttributes(qAttributes);
		activeQRepository.save(aQuestion);
	}

	@Autowired
	public void setQAttributesRepository(QuestionAttributesRepository qAttributesRepository) {
		this.qAttributesRepository = qAttributesRepository;
	}

	@Autowired
	public void setActiveQRepository(ActiveQuestionRepository activeQRepository) {
		this.activeQRepository = activeQRepository;
	}
}
