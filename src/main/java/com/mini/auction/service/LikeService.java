package com.mini.auction.service;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.entity.Like;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import com.mini.auction.repository.LikeRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;


    public ResponseDto<String> likeProduct(Member member, Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 게시물이 존재하지 않습니다.")
        );

        Optional<Like> liked = likeRepository.findAllByProductAndMember(findProduct, member);

        if(liked.isEmpty()) {
            likeRepository.save(new Like(member, findProduct));

            return ResponseDto.success(productId + "번 상품이 관심상품에 등록되었습니다.");
        }
        likeRepository.deleteById(liked.get().getId());
        return ResponseDto.success(productId + "번 상품이 관심상품에서 제거되었습니다.");
    }
}
