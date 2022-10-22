package com.mini.auction.entity;

import com.mini.auction.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class Bid extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 낙찰
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "WINNINGBID_ID")
    private WinningBid winningBid;

    public Bid(Product product, Member member) {
        this.product = product;
        this.member = member;
    }
}
