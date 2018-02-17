package pl.edu.pk.geoquiz.gqserver.model;

import java.util.List;

public class AnswerRequest {

	private String token;
	private String userAnswer;
	private List<String> possibleAnswers;

	public AnswerRequest() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
}
