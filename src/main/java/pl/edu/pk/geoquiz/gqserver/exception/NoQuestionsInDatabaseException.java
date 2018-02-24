package pl.edu.pk.geoquiz.gqserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoQuestionsInDatabaseException extends RuntimeException {
	public NoQuestionsInDatabaseException(String message) {
		super(message);
	}
}