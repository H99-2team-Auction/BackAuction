package com.mini.auction.service;

import com.mini.auction.entity.Product;
import com.mini.auction.repository.BidRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;

    /**
     * 상품 입찰
     */
    public void addBidder(String username, Long productId) {
        // 상품 존재 확인
        Product findProduct = isExistedProduct(productId);

        // BidRepository에 Member가 있는지 확인
//        if(bidRepository.findByUsername(username).isPresent()) {
//
//        }



        // 있다면 Bid 삭제
        // 없다면 Bid 추가
//        Bid bid = new Bid(findProduct);
    }

//    private isMaxBid() {
        // 최고 입찰가가 요구됨
        // bid => price
        // 특정 상품에 대한 bid의 size == 0인 경우
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
