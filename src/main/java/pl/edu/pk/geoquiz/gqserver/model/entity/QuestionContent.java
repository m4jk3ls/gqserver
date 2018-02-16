package pl.edu.pk.geoquiz.gqserver.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUESTION_CONTENT")
public class QuestionContent {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "qContent_seq")
	@SequenceGenerator(name = "qContent_seq", sequenceName = "QUESTION_CONTENT_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "CONTENT", nullable = false, unique = true)
	private String content;

	@OneToMany(mappedBy = "questionContent")
	private List<QuestionAttributes> questionAttributes;

	public QuestionContent() {
	}

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<QuestionAttributes> getQuestionAttributes() {
		return questionAttributes;
	}

	public void setQuestionAttributes(List<QuestionAttributes> questionAttributes) {
		this.questionAttributes = questionAttributes;
	}
}
