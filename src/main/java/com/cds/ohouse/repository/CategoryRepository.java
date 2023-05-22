package com.cds.ohouse.repository;

import com.cds.ohouse.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getCategoriesByCheckListId(Long checkListId);
}