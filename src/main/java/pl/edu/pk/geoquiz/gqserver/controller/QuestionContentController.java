package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pk.geoquiz.gqserver.model.QuestionContent;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionContentRepository;

import java.net.URI;

@RestController
public class QuestionContentController {

	private QuestionContentRepository questionContentRepository;

	@PostMapping(path = "/geoquiz/questionsContents")
	public ResponseEntity<Object> createQuestionContent(@RequestBody QuestionContent questionContent) {
		QuestionContent savedQContent = questionContentRepository.save(questionContent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedQContent.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Autowired
	public void setQuestionContentRepository(QuestionContentRepository questionContentRepository) {
		this.questionContentRepository = questionContentRepository;
	}
}
