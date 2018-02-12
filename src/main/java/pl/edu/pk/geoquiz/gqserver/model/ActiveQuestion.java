package pl.edu.pk.geoquiz.gqserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ACTIVE_QUESTION")
public class ActiveQuestion {

	@Id
	@Column(name = "TOKEN", nullable = false, length = 100)
	private String token;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
	private QuestionAttributes questionAttributes;

	@Column(name = "ANSWER", nullable = false, length = 1000)
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