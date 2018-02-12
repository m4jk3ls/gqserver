package pl.edu.pk.geoquiz.gqserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ARCHIVES", uniqueConstraints = {@UniqueConstraint(columnNames = {"question_attributes_id"})})
public class Archives {

	@Id
	@Column(name = "ID", nullable = false, length = 10)
	@GeneratedValue(generator = "arch_seq")
	@SequenceGenerator(name = "arch_seq", sequenceName = "ARCHIVES_SEQ", allocationSize = 1)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
	private QuestionAttributes questionAttributes;

	@Column(name = "GOOD_ANSWERS", nullable = false, columnDefinition = "NUMBER(10) DEFAULT 0 NOT NULL", length = 10)
	private Integer goodAnswers;

	@Column(name = "BAD_ANSWERS", nullable = false, columnDefinition = "NUMBER(10) DEFAULT 0 NOT NULL", length = 10)
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