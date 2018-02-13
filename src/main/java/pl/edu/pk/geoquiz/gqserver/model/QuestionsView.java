package pl.edu.pk.geoquiz.gqserver.model;

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

	@Column(name = "QUESTION_ABOUT_API")
	private String questionAboutInApi;

	@Column(name = "QUESTION_CONTEXT_API")
	private String questionContextInApi;

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

	public String getQuestionAboutInApi() {
		return questionAboutInApi;
	}

	public void setQuestionAboutInApi(String questionAboutInApi) {
		this.questionAboutInApi = questionAboutInApi;
	}

	public String getQuestionContextInApi() {
		return questionContextInApi;
	}

	public void setQuestionContextInApi(String questionContextInApi) {
		this.questionContextInApi = questionContextInApi;
	}
}
