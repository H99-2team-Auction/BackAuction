package com.mini.auction.service;

import com.mini.auction.domain.Bid;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.dto.request.BidRequestDto;
import com.mini.auction.dto.response.BidResponseDto;
import com.mini.auction.exception.bidException.AlreadySoldOutException;
import com.mini.auction.exception.bidException.FailBidException;
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
    public BidResponseDto addBid(Member member, Long productId, BidRequestDto bidRequestDto) {
        // 상품 존재 확인
        Product findProduct = isExistedProduct(productId);
        // 낙찰된 상품인지 확인
        if (findProduct.getIsSold()) throw new AlreadySoldOutException("이미 낙찰된 상품입니다.");

        Bid bid;
        // 입찰이 처음인지 확인 (Product 처음 post 할 때 highPrice 0으로 초기화)
        // 처음이면 최저입찰가와 비교, 처음 아니면 최고입찰가와 비교
        if (findProduct.getHighPrice() == 0) {
            // 최저 입찰가보다 낮은 금액을 입력하면 예외처리
            compareToLowprice(findProduct, bidRequestDto);
            // Bid 테이블에는 어떤 product 에 누가 입찰에 참여했는지만 알 수 있음
            bid = new Bid(findProduct, member, bidRequestDto.getBiddingPrice());
            // 입찰에 참여한 사람의 입찰가를 product 에 update 하는 방식으로 진행
            findProduct.updatePrice(bidRequestDto.getBiddingPrice());
            bidRepository.save(bid);
        } else {
            // 최고 입찰가보다 낮은 금액을 입력하면 예외처리
            compareToHighprice(findProduct, bidRequestDto);
            // 최고 입찰가 product 에 update
            findProduct.updatePrice(bidRequestDto.getBiddingPrice());
            bid = new Bid(findProduct, member, bidRequestDto.getBiddingPrice());
            bidRepository.save(bid);
        }

        return new BidResponseDto(bid, getBidParticipants(findProduct));
    }

    // BidRepository 에서 Product 에 입찰한 멤버 모두 불러오는 메서드
    public List<String> getBidParticipants(Product product) {
        List<Bid> bidList = bidRepository.findBidsByProduct(product);
        List<String> usernameList = new ArrayList<>();
        for (Bid bid : bidList) {
            if (usernameList.contains(bid.getMember().getUsername())) continue;
            usernameList.add(bid.getMember().getUsername());
        }
        return usernameList;
    }


    private void compareToLowprice(Product product, BidRequestDto bidRequestDto) {
//        if (product.getLowPrice() >= bidRequestDto.getBiddingPrice()) {
//            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
//        }
    }

    private void compareToHighprice(Product product, BidRequestDto bidRequestDto) {
//        if (product.getHighPrice() >= bidRequestDto.getBiddingPrice()) {
//            throw new WrongPriceException("현재 입찰가보다 높은 가격을 입력하세요.");
//        }
    }

    private Product isExistedProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );
    }


    @Transactional
    public Member winBid(Long productId) {
        // 상품 존재 확인
        Product findProduct = isExistedProduct(productId);
        // 입찰에 참여한 사람없으면 낙찰 안되고 게시물 삭제 후 예외처리
        checkSoldOrNot(findProduct);
        // bid repo 에서 Product 와 Product 에 저장된 highPrice 로 낙찰된 사람 찾기
        Bid bid = bidRepository.findBidByProductAndHighPrice(findProduct, findProduct.getHighPrice());
        // product 팔렸다는 의미로 isSold true 로 바꿈
        findProduct.soldProduct();
        // product 에 winner 저장
        findProduct.setWinner(bid);
        return bid.getMember();
    }

    // 트랜젝셔널 때문에 낙찰되어도 삭제되는건가
    private void checkSoldOrNot(Product findProduct) {
        if (findProduct.getHighPrice() == 0) {
            productRepository.delete(findProduct);
            throw new FailBidException("입찰에 참여한 분이 없습니다.");
        }
    }


}
