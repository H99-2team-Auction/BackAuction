package com.mini.auction.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id @GeneratedValue
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

//    public Likes(Member member, Product product) {
//        this.member = member;
//        this.product = product;
//    }
}
