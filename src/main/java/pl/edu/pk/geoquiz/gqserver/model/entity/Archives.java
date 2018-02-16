package pl.edu.pk.geoquiz.gqserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ARCHIVES", uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_ATTRIBUTES_ID"})})
public class Archives {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "arch_seq")
	@SequenceGenerator(name = "arch_seq", sequenceName = "ARCHIVES_SEQ", allocationSize = 1)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
	private QuestionAttributes questionAttributes;

	@Column(name = "GOOD_ANSWERS", nullable = false, columnDefinition = "NUMBER DEFAULT 0 NOT NULL")
	private Integer goodAnswers;

	@Column(name = "BAD_ANSWERS", nullable = false, columnDefinition = "NUMBER DEFAULT 0 NOT NULL")
	private Integer badAnswers;

	public Archives() {
	}

	public Integer getId() {
		return id;
	}

	public QuestionAttributes getQuestionAttributes() {
		return questionAttributes;
	}

	public void setQuestionAttributes(QuestionAttributes questionAttributes) {
		this.questionAttributes = questionAttributes;
	}

	public Integer getGoodAnswers() {
		return goodAnswers;
	}

	public void setGoodAnswers(Integer goodAnswers) {
		this.goodAnswers = goodAnswers;
	}

	public Integer getBadAnswers() {
		return badAnswers;
	}

	public void setBadAnswers(Integer badAnswers) {
		this.badAnswers = badAnswers;
	}
}