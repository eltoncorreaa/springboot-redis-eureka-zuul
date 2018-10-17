package  com.elton.app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXPENSE")
@SequenceGenerator(name = "SEQUENCE_EXPENSE", sequenceName = "SEQUENCE_EXPENSE")
@Getter @Setter @EqualsAndHashCode
public class Expense implements Serializable {

	private static final long serialVersionUID = -8507622473380945770L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQUENCE_EXPENSE")
	@Column(name = "ID_EXPENSE", precision = 12, scale = 0)
	private Long id;

	@Column(name = "VALUE", nullable = false)
	private double value;

	@Column(name = "USER_CODE", nullable = false)
	private Long userCode;

	@Column(name = "EXPENSE_DATE", nullable = false)
	private LocalDateTime expenseDate;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Integer version;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_CATEGORY", referencedColumnName = "ID_CATEGORY", nullable = true)
	private Category category;
}