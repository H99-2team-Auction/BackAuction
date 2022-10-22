package com.mini.auction.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Table(name = "likes")
@Getter
public class Like {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Like(Member member, Product product) {
        this.member = member;
        this.product = product;
    }
}
