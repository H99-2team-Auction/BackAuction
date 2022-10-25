package com.mini.auction.domain;

import com.mini.auction.domain.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class Bid extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;
//
//    @Column
//    private Integer biddingPrice;

    @Column
    private Integer participantCnt = 0;


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



    public Bid(Product product, Member member, @NotBlank(message = "입찰가를 입력해 주세요") Integer biddingPrice) {
        this.product = product;
        this.member = member;
//        this.biddingPrice = biddingPrice;
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
