package com.sda.store.repository;

import com.sda.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Long> {
        Optional<Category> findByName(String name);
        List<Category> findAllByParentNull();
}
