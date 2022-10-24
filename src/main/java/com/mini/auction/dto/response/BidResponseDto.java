package com.mini.auction.dto.response;

import com.mini.auction.domain.Bid;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import lombok.Getter;

import java.util.List;

@Getter
public class BidResponseDto {

    private Integer biddingPrice;

    private Integer participantCnt;

    private String title;

    private String username;

    List<String> bidParticipants;


    public  BidResponseDto(Bid bid, Product product, Member member, List<String> bidParticipants) {
        this.biddingPrice = bid.getBiddingPrice();
        this.title = product.getTitle();
        this.username = member.getUsername();
        this.bidParticipants = bidParticipants;
        this.participantCnt = bidParticipants.size();
    }

}
