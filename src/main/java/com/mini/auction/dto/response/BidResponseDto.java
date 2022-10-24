package com.mini.auction.dto.response;

import com.mini.auction.domain.Bid;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidResponseDto {

    private Integer biddingPrice;

    private Integer participantCnt;

    private String title;

    private String username;

    List<String> bidParticipants;

    public static List<String> getParticipantList(List<Product> productList) {
        List<String> participantList = new ArrayList<>();
        for (Product product : productList) {
            participantList.add(product.getMember().getUsername());
        }
        return participantList;
    }

    public BidResponseDto(Bid bid, Product product, Member member) {
        this.biddingPrice = bid.getBiddingPrice();
        this.title = product.getTitle();
        this.username = member.getUsername();
        this.participantCnt = bidParticipants.size();
    }

}
