package pl.edu.pk.geoquiz.gqserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pk.geoquiz.gqserver.model.QuestionContent;
import pl.edu.pk.geoquiz.gqserver.repository.QuestionContentRepository;

import java.net.URI;
import java.util.Optional;

@RestController
public class QuestionContentController {

	private QuestionContentRepository qContentRepository;

	@GetMapping(path = "/geoquiz/qContent/{id}")
	public Resource<QuestionContent> retrieveQuestionContent(@PathVariable int id) {
		Optional<QuestionContent> qContentOptional = qContentRepository.findById(id);
		return new Resource<>(qContentOptional.get());
	}

	@PostMapping(path = "/geoquiz/qContent")
	public ResponseEntity<Object> createQuestionContent(@RequestBody QuestionContent qContent) {
		QuestionContent savedQContent = qContentRepository.save(qContent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedQContent.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Autowired
	public void setQContentRepository(QuestionContentRepository qContentRepository) {
		this.qContentRepository = qContentRepository;
	}
}