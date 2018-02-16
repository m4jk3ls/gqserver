package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionAttributes;
import pl.edu.pk.geoquiz.gqserver.model.entity.QuestionContent;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionAttributesRepository;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionContentRepository;

import java.net.URI;
import java.util.Optional;

@RestController
public class QuestionAttributesController {

	private QuestionContentRepository qContentRepository;
	private QuestionAttributesRepository qAttributesRepository;

	@PostMapping(path = "/geoquiz/qContent/{id}/qAttributes")
	public ResponseEntity<Object> createQuestionAttribute(@PathVariable int id, @RequestBody QuestionAttributes qAttributes) {
		Optional<QuestionContent> qContentOptional = qContentRepository.findById(id);
		QuestionContent qContent = qContentOptional.get();
		qAttributes.setQuestionContent(qContent);
		qAttributesRepository.save(qAttributes);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(qAttributes.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Autowired
	public void setQContentRepository(QuestionContentRepository qContentRepository) {
		this.qContentRepository = qContentRepository;
	}

	@Autowired
	public void setQAttributesRepository(QuestionAttributesRepository qAttributesRepository) {
		this.qAttributesRepository = qAttributesRepository;
	}
}
