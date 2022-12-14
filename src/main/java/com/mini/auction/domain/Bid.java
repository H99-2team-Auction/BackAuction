package com.mini.auction.domain;

import com.mini.auction.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class Bid extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer highPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    public Bid(Product product, Member member, Integer highPrice) {
        this.product = product;
        this.member = member;
        this.highPrice = highPrice;
    }
//
//    // 가격 업데이트
//    public void update(Integer biddingPrice, Member member) {
//        this.biddingPrice = biddingPrice;
//        this.member = member;
//    }
//
//    // 입찰에 참여한 사람 수
//    public void addParticipant() {
//        this.participantCnt++;
//    }
}
