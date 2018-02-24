package pl.edu.pk.geoquiz.gqserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionExpiredException extends RuntimeException {
	public QuestionExpiredException(String message) {
		super(message);
	}
}