package pl.edu.pk.geoquiz.gqserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUESTION_ATTRIBUTES", uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_CONTENT_ID", "QUESTION_ATTRIBUTE", "QUESTION_ABOUT"})})
public class QuestionAttributes {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "qAttributes_seq")
	@SequenceGenerator(name = "qAttributes_seq", sequenceName = "QUESTION_ATTRIBUTES_SEQ", allocationSize = 1)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private QuestionContent questionContent;

	@OneToMany(mappedBy = "questionAttributes")
	private List<ActiveQuestion> activeQuestion;

	@OneToMany(mappedBy = "questionAttributes")
	private List<Archives> archives;

	@Column(name = "QUESTION_ATTRIBUTE", nullable = false, length = 50)
	private String questionAttribute;

	@Column(name = "ATTRIBUTE_POSITION", nullable = false)
	private Integer attributePosition;

	@Column(name = "QUESTION_ABOUT", nullable = false, length = 50)
	private String questionAboutApi;

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

	public List<ActiveQuestion> getActiveQuestion() {
		return activeQuestion;
	}

	public void setActiveQuestion(List<ActiveQuestion> activeQuestion) {
		this.activeQuestion = activeQuestion;
	}

	public List<Archives> getArchives() {
		return archives;
	}

	public void setArchives(List<Archives> archives) {
		this.archives = archives;
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
}