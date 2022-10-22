package com.mini.auction.service;

import com.mini.auction.entity.WinningBid;
import com.mini.auction.repository.WinningBidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WinningBidService {

    private final WinningBidRepository winningBidRepository;

    public void auctionedOff(Long productId) {
        WinningBid findBid = winningBidRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 상품입니다.")
        );
//        findBid.successBid();

        // Member에 findProduct를 넣어준다

    }

}
