package com.mini.auction.controller;

import com.mini.auction.service.WinningBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class WinningBidController {

    private final WinningBidService winningBidService;

    /**
     * 상품 낙찰
     */
    @PostMapping("/{productId}/sold")
    public void successBid(@PathVariable Long productId) {
//        productService.auctionedOff(productId);
    }


}
