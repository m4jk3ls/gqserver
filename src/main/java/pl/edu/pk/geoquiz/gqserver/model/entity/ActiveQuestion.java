package pl.edu.pk.geoquiz.gqserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ACTIVE_QUESTION")
public class ActiveQuestion {

	private static final int TOKEN_LENGTH = 100;
	private static final int ANSWER_LENGTH = 1000;

	@Id
	@Column(name = "TOKEN", nullable = false, length = TOKEN_LENGTH)
	private String token;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private QuestionAttributes questionAttributes;

	@Column(name = "ANSWER", nullable = false, length = ANSWER_LENGTH)
	private String answer;

	@Column(name = "CREATION_DATE", nullable = false)
	private Timestamp creationDate;

	public ActiveQuestion() {
	}

	public ActiveQuestion(String token, String answer, Timestamp creationDate) {
		this.token = token;
		this.answer = answer;
		this.creationDate = creationDate;
	}

	public static int getTokenLength() {
		return TOKEN_LENGTH;
	}

	public static int getAnswerLength() {
		return ANSWER_LENGTH;
	}

	public String getToken() {
		return token;
	}

	public QuestionAttributes getQuestionAttributes() {
		return questionAttributes;
	}

	public void setQuestionAttributes(QuestionAttributes questionAttributes) {
		this.questionAttributes = questionAttributes;
	}

	public String getAnswer() {
		return answer;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
}