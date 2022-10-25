package com.mini.auction.service;

import com.mini.auction.domain.Bid;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.dto.request.BidRequestDto;
import com.mini.auction.dto.response.BidResponseDto;
import com.mini.auction.exception.WrongPriceException;
import com.mini.auction.repository.BidRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;

    /**
     * 상품 입찰
     */
    @Transactional
    public BidResponseDto addBidder(Member member, Long productId, BidRequestDto bidRequestDto) {
        // 상품 존재 확인
        Product findProduct = isExistedProduct(productId);
        // 입찰한 사람이 있는지 확인
        Bid bid = bidRepository.findBidByProduct(findProduct).orElse(null);

        // 입찰이 처음인지 확인 (처음이면 최저입찰가와 비교, 처음 아니면 최고입찰가와 비교)
        if (bid == null) {
            // 최저 입찰가보다 낮은 금액을 입력하면 예외처리
            compareToLowprice(findProduct, bidRequestDto);
            // Bid 테이블에는 어떤 product 에 누가 입찰에 참여했는지만 알 수 있음
            bid = new Bid(findProduct, member);
            // 입찰에 참여한 사람의 입찰가를 product 에 update 하는 방식으로 진행
            findProduct.updatePrice(bidRequestDto.getBiddingPrice());
            bidRepository.save(bid);
        } else {
            // 최고 입찰가보다 낮은 금액을 입력하면 예외처리
            compareToHighprice(findProduct, bidRequestDto);
            // 최고 입찰가 product 에 update
            findProduct.updatePrice(bidRequestDto.getBiddingPrice());
            bidRepository.save(new Bid(findProduct, member));
        }

        return new BidResponseDto(bid, getBidParticipants(findProduct));
    }

    // BidRepository 에서 Product 에 입찰한 멤버 모두 불러오는 메서드
    private List<String> getBidParticipants(Product product) {
        List<Bid> bidList = bidRepository.findBidsByProduct(product);
        List<String> usernameList = new ArrayList<>();
        for (Bid bid : bidList) {
            if (usernameList.contains(bid.getMember().getUsername())) continue;
            usernameList.add(bid.getMember().getUsername());
        }
        return usernameList;
    }


    private void compareToLowprice(Product product, BidRequestDto bidRequestDto) {
        if (product.getLowPrice() >= bidRequestDto.getBiddingPrice()) {
            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
        }
    }

    private void compareToHighprice(Product product, BidRequestDto bidRequestDto) {
        if (product.getHighPrice() >= bidRequestDto.getBiddingPrice()) {
            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
        }
    }

    private Product isExistedProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );
    }


}
