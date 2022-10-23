package com.mini.auction.service;

import com.mini.auction.dto.request.BidRequestDto;
import com.mini.auction.dto.response.BidResponseDto;
import com.mini.auction.entity.Bid;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import com.mini.auction.exception.WrongPriceException;
import com.mini.auction.repository.BidRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            // db 에 bid 저장
            bidRepository.save(new Bid(findProduct, member, bidRequestDto.getBiddingPrice()));
        } else {
            // 최고 입찰가보다 낮은 금액을 입력하면 예외처리
            compareToHighprice(bid, bidRequestDto);
            bid.update(bidRequestDto.getBiddingPrice());
        }
        // bid null 일수도 있다고 인텔리제이에서 알려주는데 if문 지나면 null 아닌데 어떻게 풀어야하지????
        bid.addParticipant();

        return new BidResponseDto(bid);
    }

    private void compareToLowprice(Product product, BidRequestDto bidRequestDto) {
        if (product.getLowPrice() > bidRequestDto.getBiddingPrice()) {
            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
        }
    }

    private void compareToHighprice(Bid bid, BidRequestDto bidRequestDto) {
        if (bid.getBiddingPrice() > bidRequestDto.getBiddingPrice()) {
            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
        }
    }

//    private isMaxBid() {
    // 최고 입찰가가 요구됨
    // bid => price
    // 특정 상품에 대한 bid 의 size == 0인 경우
    // 최저값 보다 큰지를 확인해서 크다면 저장하고
    // size != 0아닌 경우 즉, 1개 이상인 경우라면
    // 전체의 price 중 최대 값인지 확인한다.
//        bidRepository.findBy == Max?????
//    }

    private Product isExistedProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );
        return findProduct;
    }


}
