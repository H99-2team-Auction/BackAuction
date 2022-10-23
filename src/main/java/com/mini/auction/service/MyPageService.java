package com.mini.auction.service;

import com.mini.auction.dto.response.ProductResponseDto;
import com.mini.auction.dto.response.ProductResponseDto.CommonProductResponseDto;
import com.mini.auction.entity.*;
import com.mini.auction.repository.BidRepository;
import com.mini.auction.repository.LikeRepository;
import com.mini.auction.repository.ProductRepository;
import com.mini.auction.repository.WinningBidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final WinningBidRepository winningBidRepository;
    private final BidRepository bidRepository;
    private final LikeRepository likeRepository;

    /**
     * 판매한 상품 목록 조회
     * @param member
     * @return
     */
//    public List<CommonProductResponseDto> getSoldProductList(Member member) {
//        // sql 로 db 에서 찾는게 효율적인지 서버에서 찾는게 효율적인지?
//        // 왜 자꾸 자동완성이 isSold 라고 안뜨고 SoldIs 라고 뜸?
//
//        // product 객체 responseDto 에 각각 넣기
//    }

    /**
     * 입찰한 상품 목록 조회
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
