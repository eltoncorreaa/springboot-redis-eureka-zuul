package com.elton.app.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExpenseDTO extends ResourceSupport{

	private Long code;
	private String description;
	private double value;
	private Long userCode;
	@DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime date;
	private Integer version;
}