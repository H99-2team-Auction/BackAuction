package com.mini.auction.dto.response;

import com.mini.auction.domain.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WinBidResponseDto {

    private String username;

    private Integer biddingPrice;

    private String productTitle;

    public WinBidResponseDto(Bid bid) {
        this.username = bid.getMember().getUsername();
        this. biddingPrice = bid.getHighPrice();
        this.productTitle = bid.getProduct().getTitle();
    }
}
