package com.mini.auction.domain;

import com.mini.auction.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "likes")
@Getter
public class Like extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Like(Member member, Product product) {
        this.member = member;
        this.product = product;
    }
}
