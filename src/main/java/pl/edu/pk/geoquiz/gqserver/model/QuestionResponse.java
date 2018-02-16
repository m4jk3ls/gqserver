package pl.edu.pk.geoquiz.gqserver.model;

import java.util.List;

public class QuestionResponse {

	private String token;
	private String fullQuestion;
	private List<String> possibleAnswers;
	private String countryToShow;

	public QuestionResponse(String token, String fullQuestion, List<String> possibleAnswers, String countryToShow) {
		this.token = token;
		this.fullQuestion = fullQuestion;
		this.possibleAnswers = possibleAnswers;
		this.countryToShow = countryToShow;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFullQuestion() {
		return fullQuestion;
	}

	public void setFullQuestion(String fullQuestion) {
		this.fullQuestion = fullQuestion;
	}

	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public String getCountryToShow() {
		return countryToShow;
	}

	public void setCountryToShow(String countryToShow) {
		this.countryToShow = countryToShow;
	}

	@Override
	public String toString() {
		return "QuestionResponse{" +
				"token='" + token + '\'' +
				", fullQuestion='" + fullQuestion + '\'' +
				", possibleAnswers=" + possibleAnswers +
				", countryToShow='" + countryToShow + '\'' +
				'}';
	}
}
