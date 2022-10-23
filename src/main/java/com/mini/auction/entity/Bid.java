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

    @Column
    private Integer biddingPrice;

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

    public Bid(Product product, Member member, Integer biddingPrice) {
        this.product = product;
        this.member = member;
        this.biddingPrice = biddingPrice;
    }

    // 가격 업데이트
    public void update(Integer biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    // 입찰에 참여한 사람 수
    public void addParticipant() {
        this.participantCnt++;
    }
}
