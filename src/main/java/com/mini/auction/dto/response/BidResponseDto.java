package com.mini.auction.dto.response;

import com.mini.auction.entity.Bid;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import lombok.Getter;

@Getter
public class BidResponseDto {

    private Integer biddingPrice;

    private Integer participantCnt;

    private Product product;

    private Member member;

    public  BidResponseDto(Bid bid) {
        this.biddingPrice = bid.getBiddingPrice();
        this.participantCnt = bid.getParticipantCnt();
        this.product = bid.getProduct();
        this.member = bid.getMember();
    }

}
