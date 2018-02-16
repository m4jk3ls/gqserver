package pl.edu.pk.geoquiz.gqserver.model;

public class AnswerRequest {

	private String token;
	private String answer;

	public AnswerRequest() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
