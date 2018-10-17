package com.elton.app.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.elton.app.domain.Category;
import com.elton.app.dto.CategoryDTO;

public class CategoryConverter {

	public static CategoryDTO fromDomain(final Category model) {
		final CategoryDTO dto = new CategoryDTO();
		dto.setCode(model.getId());
		dto.setDescription(model.getDescription());
		return dto;
	}

	public static List<CategoryDTO> fromDomain(final List<Category> listModel) {
		return listModel.stream().map(CategoryConverter::fromDomain).collect(Collectors.toList());
	}
}