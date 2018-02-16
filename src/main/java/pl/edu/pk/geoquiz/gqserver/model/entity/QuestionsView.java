package pl.edu.pk.geoquiz.gqserver.model.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "QUESTIONS_VIEW")
public class QuestionsView {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "ATTRIBUTE")
	private String attribute;

	@Column(name = "ATTRIBUTE_POSITION")
	private Integer attributePosition;

	@Column(name = "QUESTION_ABOUT")
	private String questionAbout;

	public QuestionsView() {
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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Integer getAttributePosition() {
		return attributePosition;
	}

	public void setAttributePosition(Integer attributePosition) {
		this.attributePosition = attributePosition;
	}

	public String getQuestionAbout() {
		return questionAbout;
	}

	public void setQuestionAbout(String questionAbout) {
		this.questionAbout = questionAbout;
	}
}
