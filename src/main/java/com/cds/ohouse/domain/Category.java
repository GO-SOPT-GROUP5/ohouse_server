package com.cds.ohouse.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
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
}