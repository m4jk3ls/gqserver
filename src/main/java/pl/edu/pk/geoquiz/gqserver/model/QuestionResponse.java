package pl.edu.pk.geoquiz.gqserver.model;

import java.util.List;

public class QuestionResponse {

	private String token;
	private String fullQuestion;
	private List<String> possibleAnswers;
	private String countryToShow;
	private int goodAnswers;
	private int badAnswers;

	public QuestionResponse() {
	}

	public QuestionResponse(String token, String fullQuestion, List<String> possibleAnswers, String countryToShow, int goodAnswers, int badAnswers) {
		this.token = token;
		this.fullQuestion = fullQuestion;
		this.possibleAnswers = possibleAnswers;
		this.countryToShow = countryToShow;
		this.goodAnswers = goodAnswers;
		this.badAnswers = badAnswers;
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

	public int getGoodAnswers() {
		return goodAnswers;
	}

	public void setGoodAnswers(int goodAnswers) {
		this.goodAnswers = goodAnswers;
	}

	public int getBadAnswers() {
		return badAnswers;
	}

	public void setBadAnswers(int badAnswers) {
		this.badAnswers = badAnswers;
	}

	@Override
	public String toString() {
		return "QuestionResponse{" +
				"token='" + token + '\'' +
				", fullQuestion='" + fullQuestion + '\'' +
				", possibleAnswers=" + possibleAnswers +
				", countryToShow='" + countryToShow + '\'' +
				", goodAnswers=" + goodAnswers +
				", badAnswers=" + badAnswers +
				'}';
	}
}
