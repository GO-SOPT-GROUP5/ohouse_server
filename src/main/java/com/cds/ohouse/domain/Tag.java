package com.cds.ohouse.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TradeState state;

    @Column(name = "price")
    private String price;

    @Column(name = "size")
    private int size;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    public Tag(TradeState state, String price, int size) {
        this.state = state;
        this.price = price;
        this.size = size;
    }

    public void updateTag(Tag tag) {
        this.state = tag.getState();
        this.price = tag.getPrice();
        this.size = tag.getSize();
    }


    public static Tag newInstance(TradeState state, String price, int size) {
        return new Tag(state, price, size);
    }
}
