package com.mini.auction.dto.response;

import com.mini.auction.domain.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BidResponseDto {

    private Integer biddingPrice;

    private Integer participantCnt;

    private String title;

    private String username;

    private List<String> usernameList;


    public  BidResponseDto(Bid bid, List<String> usernameList) {
        this.biddingPrice = bid.getProduct().getHighPrice();
        this.title = bid.getProduct().getTitle();
        this.username = bid.getMember().getUsername();
        this.usernameList = usernameList;
        this.participantCnt = usernameList.size();
    }

}
