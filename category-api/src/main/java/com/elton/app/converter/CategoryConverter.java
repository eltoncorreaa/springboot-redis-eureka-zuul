package com.elton.app.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.elton.app.dto.CategoryDTO;
import com.elton.app.model.Category;

public class CategoryConverter {

	public static CategoryDTO toDTO(final Category model) {
		final CategoryDTO dto = new CategoryDTO();
		dto.setCode(model.getId());
		dto.setDescription(model.getDescription());
		return dto;
	}

	public static List<CategoryDTO> toDTO(final List<Category> listModel) {
		return listModel.stream().map(CategoryConverter::toDTO).collect(Collectors.toList());
	}
}
