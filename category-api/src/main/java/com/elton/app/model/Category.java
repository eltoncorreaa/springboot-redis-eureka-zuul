package  com.elton.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
@EqualsAndHashCode
public class Category implements Serializable {

	private static final long serialVersionUID = -448760385229721893L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CATEGORY", precision = 12, scale = 0)
	private Long id;

	@Column(name = "DESCRIPTION", nullable = false, length = 200)
	private String description;
}