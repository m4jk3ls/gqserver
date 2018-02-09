package pl.edu.pk.geoquiz.gqserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "QUESTION_CONTENT")
public class QuestionContent {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "qcontent_seq")
	@SequenceGenerator(name = "qcontent_seq", sequenceName = "QUESTION_CONTENT_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "CONTENT", nullable = false, unique = true)
	private String content;

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
}
