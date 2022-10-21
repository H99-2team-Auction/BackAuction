package com.mini.auction.controller;

import com.mini.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/product/{productId}/bid")
    public void bidding(@PathVariable Long productId,
                        @AuthenticationPrincipal UserDetails userDetails) {
        bidService.addBidder(userDetails.getUsername(), productId);

    }


}
