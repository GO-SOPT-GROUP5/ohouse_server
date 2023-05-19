package com.cds.ohouse.repository;

import com.cds.ohouse.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    Optional<CheckList> findCheckListById(Long id);
}
