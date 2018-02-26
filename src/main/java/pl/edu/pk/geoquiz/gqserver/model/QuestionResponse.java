package pl.edu.pk.geoquiz.gqserver.model;

import java.util.List;

public class QuestionResponse {

	private String token;
	private String fullQuestion;
	private List<String> possibleAnswers;
	private String countryName;
	private String countryAlpha2Code;
	private String goodAnswers;
	private String badAnswers;

	public QuestionResponse() {
	}

	public QuestionResponse(String token, String fullQuestion, List<String> possibleAnswers, String countryName, String countryAlpha2Code, String goodAnswers, String badAnswers) {
		this.token = token;
		this.fullQuestion = fullQuestion;
		this.possibleAnswers = possibleAnswers;
		this.countryName = countryName;
		this.countryAlpha2Code = countryAlpha2Code;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryAlpha2Code() {
		return countryAlpha2Code;
	}

	public void setCountryAlpha2Code(String countryAlpha2Code) {
		this.countryAlpha2Code = countryAlpha2Code;
	}

	public String getGoodAnswers() {
		return goodAnswers;
	}

	public void setGoodAnswers(String goodAnswers) {
		this.goodAnswers = goodAnswers;
	}

	public String getBadAnswers() {
		return badAnswers;
	}

	public void setBadAnswers(String badAnswers) {
		this.badAnswers = badAnswers;
	}

	@Override
	public String toString() {
		return "QuestionResponse{" +
				"token='" + token + '\'' +
				", fullQuestion='" + fullQuestion + '\'' +
				", possibleAnswers=" + possibleAnswers +
				", countryName='" + countryName + '\'' +
				", goodAnswers=" + goodAnswers +
				", badAnswers=" + badAnswers +
				'}';
	}
}
