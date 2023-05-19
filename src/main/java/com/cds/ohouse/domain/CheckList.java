package com.cds.ohouse.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
public class CheckList {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "check_list_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "grade")
    private int grade;

    @Column(name = "address")
    private String address;

    @Column(name = "dong")
    private int dong;

    @Column(name = "ho")
    private int ho;

    @OneToOne(mappedBy = "checkList")
    private Tag tag;

    @OneToMany(mappedBy = "checkList")
    List<Category> categories =  new ArrayList<>();

    @Builder
    public CheckList(String title, String image, String description, int grade, String address, int dong, int ho, Tag tag) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.grade = grade;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.tag = tag;
    }
}