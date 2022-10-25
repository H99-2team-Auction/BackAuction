package com.mini.auction.service;

import com.mini.auction.dto.response.ProductResponseDto.CommonProductResponseDto;
import com.mini.auction.domain.*;
import com.mini.auction.repository.BidRepository;
import com.mini.auction.repository.LikeRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final BidRepository bidRepository;
    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;

    /**
     * 판매한 상품 목록 조회
     *
     * @param member
     * @return
     */
    public List<CommonProductResponseDto> getSoldProductList(Member member) {
        // isSold 가 true 이고 member 가 게시한 product 조회
        List<Product> soldProductList = productRepository.findProductsByIsSoldAndMember(true, member);
        // dto 에 담아 리턴
        List<CommonProductResponseDto> responseDtoList = new ArrayList<>();
        for (Product product : soldProductList) {
            responseDtoList.add(new CommonProductResponseDto(product));
        }
        return responseDtoList;
    }

    /**
     * 입찰한 상품 목록 조회
     *
     * @param member
     * @return
     */
    public List<CommonProductResponseDto> getBidList(Member member) {
        List<Bid> bidList = bidRepository.findBidsByMember(member);

        List<CommonProductResponseDto> responseDtoList = new ArrayList<>();
        for (Bid bid : bidList) {
            responseDtoList.add(new CommonProductResponseDto(bid.getProduct()));
        }
        return responseDtoList;
    }

    /**
     * 관심 등록한 상품 목록 조회
     *
     * @param member
     * @return
     */
    public List<CommonProductResponseDto> getLikeList(Member member) {
        List<Like> likeList = likeRepository.findLikesByMember(member);
        List<CommonProductResponseDto> responseDtoList = new ArrayList<>();
        for (Like like : likeList) {
            responseDtoList.add(new CommonProductResponseDto(like.getProduct()));
        }
        return responseDtoList;
    }
}
