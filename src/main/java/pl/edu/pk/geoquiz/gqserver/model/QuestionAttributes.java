package pl.edu.pk.geoquiz.gqserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION_ATTRIBUTES", uniqueConstraints = {@UniqueConstraint(columnNames = {"question_content_id", "question_about_api", "question_context_api"})})
public class QuestionAttributes {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "qAttributes_seq")
	@SequenceGenerator(name = "qAttributes_seq", sequenceName = "QUESTION_ATTRIBUTES_SEQ", allocationSize = 1)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private QuestionContent questionContent;

	@Column(name = "QUESTION_ATTRIBUTE", nullable = false, length = 50)
	private String questionAttribute;

	@Column(name = "ATTRIBUTE_POSITION", nullable = false, length = 2)
	private Integer attributePosition;

	@Column(name = "QUESTION_ABOUT_API", nullable = false, length = 50)
	private String questionAboutApi;

	@Column(name = "QUESTION_CONTEXT_API", nullable = false, length = 50)
	private String questionContextApi;

	public QuestionAttributes() {
	}

	public Integer getId() {
		return id;
	}

	public QuestionContent getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(QuestionContent questionContent) {
		this.questionContent = questionContent;
	}

	public String getQuestionAttribute() {
		return questionAttribute;
	}

	public void setQuestionAttribute(String questionAttribute) {
		this.questionAttribute = questionAttribute;
	}

	public Integer getAttributePosition() {
		return attributePosition;
	}

	public void setAttributePosition(Integer attributePosition) {
		this.attributePosition = attributePosition;
	}

	public String getQuestionAboutApi() {
		return questionAboutApi;
	}

	public void setQuestionAboutApi(String questionAboutApi) {
		this.questionAboutApi = questionAboutApi;
	}

	public String getQuestionContextApi() {
		return questionContextApi;
	}

	public void setQuestionContextApi(String questionContextApi) {
		this.questionContextApi = questionContextApi;
	}
}