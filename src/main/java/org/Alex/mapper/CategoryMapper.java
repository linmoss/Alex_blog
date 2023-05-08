package org.Alex.mapper;

import org.Alex.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMapper extends JpaRepository<Category, Integer> {
}
