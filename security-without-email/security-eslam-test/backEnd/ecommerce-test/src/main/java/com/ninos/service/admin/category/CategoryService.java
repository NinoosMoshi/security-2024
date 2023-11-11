package com.ninos.service.admin.category;

import java.util.List;

import com.ninos.model.dto.CategoryDTO;
import com.ninos.model.entity.Category;

public interface CategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();

}
