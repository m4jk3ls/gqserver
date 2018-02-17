package pl.edu.pk.geoquiz.gqserver.model;

public class AnswerResponse {

	private boolean correct;
	private String correctAnswer;

	public AnswerResponse() {
	}

	public AnswerResponse(boolean correct, String correctAnswer) {
		this.correct = correct;
		this.correctAnswer = correctAnswer;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}