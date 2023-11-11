package com.ninos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninos.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}