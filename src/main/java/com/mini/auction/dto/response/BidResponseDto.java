package com.mini.auction.dto.response;

import com.mini.auction.entity.Bid;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BidResponseDto {

    private Integer biddingPrice;

    private Integer participantCnt;

    private String title;

    private String username;

    List<String> memberList = new ArrayList<>();


    public  BidResponseDto(Bid bid, Product product, Member member) {
        this.biddingPrice = bid.getBiddingPrice();
        this.title = product.getTitle();
        this.username = member.getUsername();
        this.memberList.add(member.getUsername());
        this.participantCnt = memberList.size();
    }

}
