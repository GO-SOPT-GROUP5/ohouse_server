package com.cds.ohouse.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryStatus category;

    @Enumerated(EnumType.STRING)
    private SubCategoryStatus subCategory;

    @Column(name = "state")
    private int state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    public Category(CategoryStatus category, SubCategoryStatus subCategory, int state, CheckList checkList) {
        this.category = category;
        this.subCategory = subCategory;
        this.state = state;
        setCheckList(checkList);
    }

    private void setCheckList(CheckList checkList) {
        if (Objects.nonNull(this.checkList)) {
            this.checkList.getCategories().remove(this);
        }
        this.checkList = checkList;
        checkList.getCategories().add(this);
    }
}