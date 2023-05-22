package com.cds.ohouse.domain;

import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
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

    @Column(name = "good")
    private int good;

    @Column(name = "average")
    private int average;

    @Column(name = "bad")
    private int bad;

    @OneToOne(mappedBy = "checkList", cascade = CascadeType.PERSIST)
    private Tag tag;

    @OneToMany(mappedBy = "checkList")
    List<Category> categories =  new ArrayList<>();

    @Builder
    public CheckList(
            String title,
            String image,
            String description,
            int grade,
            String address,
            int dong,
            int ho,
            int good,
            int average,
            int bad,
            Tag tag,
            List<Category> categories
    ) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.grade = grade;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.good = good;
        this.average = average;
        this.bad = bad;
        this.tag = tag;
        this.categories = categories;
    }

    public void updateCategory(CheckListUpdateRequestDTO checkListUpdateRequestDTO) {
        this.title = checkListUpdateRequestDTO.getTitle();
        this.image = checkListUpdateRequestDTO.getImage();
        this.description = checkListUpdateRequestDTO.getDescription();
        this.grade = checkListUpdateRequestDTO.getGrade();
        this.address = checkListUpdateRequestDTO.getAddress();
        this.dong = checkListUpdateRequestDTO.getDong();
        this.ho = checkListUpdateRequestDTO.getHo();
    }
}