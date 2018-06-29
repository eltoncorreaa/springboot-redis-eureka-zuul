package  com.elton.app.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends ResourceSupport{

	private Long code;
	private String description;
}