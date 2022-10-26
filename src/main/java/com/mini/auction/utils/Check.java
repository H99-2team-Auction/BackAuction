package com.mini.auction.utils;

import com.mini.auction.domain.Comment;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.exception.CommentExceptions.NotAuthorException;
import com.mini.auction.exception.ErrorCode;
import com.mini.auction.exception.GlobalException;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.MemberRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;


    //가입한 회원인지 아닌지 유효성 검사해주는 method
    public Member isPresentMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }
    /*
    * 제목 작성 확인
    * */
    public void checkTitle(String title){
        if(null == title){
            throw new GlobalException(ErrorCode.TITLE_NOT_FOUND);
        }
    }
    /*
    * 최저가 작성 확인
    * */
    public void checkLowPrice(Integer lowPrice){
        if(null == lowPrice){
            throw new GlobalException(ErrorCode.LOW_PRICE_NOT_FOUND);
        }
    }
    /*
    * 내용 작성 확인
    * */
    public void checkContent(String content){
        if(null == content){
            throw new GlobalException(ErrorCode.CONTENT_NOT_FOUND);
        }
    }
    /**
     * Product 존재 유무 확인
     */
    public void isExistedProduct(Long productId) {
        productRepository.findById(productId).orElseThrow(
                () -> new GlobalException(ErrorCode.PRODUCT_NOT_FOUND)
        );
    }
    /*
    * Comment 존재 유무 확인
    * */
    public Comment isExistedComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );
        return findComment;
    }

}
