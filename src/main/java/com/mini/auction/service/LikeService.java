package com.mini.auction.service;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.domain.Like;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.repository.LikeRepository;
import com.mini.auction.repository.ProductRepository;
import com.mini.auction.utils.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;
    private final Check check;


    public ResponseDto<String> likeProduct(Member member, Long productId) {
        //상품 존재 유무 확인
        Product findProduct = check.isExistedProduct(productId);

        Optional<Like> liked = likeRepository.findAllByProductAndMember(findProduct, member);

        if(liked.isEmpty()) {
            likeRepository.save(new Like(member, findProduct));

            return ResponseDto.success(productId + "번 상품이 관심상품에 등록되었습니다.");
        }
        likeRepository.deleteById(liked.get().getId());
        return ResponseDto.success(productId + "번 상품이 관심상품에서 제거되었습니다.");
    }
    // productId를 대신할 data 가 필요할 것 같습니다~!(예를 들면 title????)
}
