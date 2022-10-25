package com.mini.auction.controller;

import com.mini.auction.domain.Member;
import com.mini.auction.dto.request.BidRequestDto;
import com.mini.auction.dto.response.BidResponseDto;
import com.mini.auction.dto.response.WinBidResponseDto;
import com.mini.auction.service.BidService;
import com.mini.auction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/{productId}")
public class BidController {
    private final BidService bidService;

    @PostMapping("/bid")
    public ResponseEntity<?> addBid(@PathVariable Long productId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody BidRequestDto bidRequestDto) {
        BidResponseDto responseDto = bidService.addBid(userDetails.getMember(), productId, bidRequestDto);
        return new ResponseEntity<>(responseDto, setHeaders(), HttpStatus.OK);
    }


    @PostMapping("/winning-bid")
    public ResponseEntity<?> winBid(@PathVariable Long productId) {
        WinBidResponseDto responseDto = bidService.winBid(productId);
        return new ResponseEntity<>(responseDto, setHeaders(), HttpStatus.OK);
    }

    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

}
