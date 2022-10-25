package com.mini.auction.util;

import com.mini.auction.domain.Member;
import com.mini.auction.exception.ErrorCode;
import com.mini.auction.exception.GlobalException;
import com.mini.auction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {

    private final MemberRepository memberRepository;

    //가입한 회원인지 아닌지 유효성 검사해주는 method
    public Member isPresentMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }
    //제목 확인
    public void checkTitle(String title){
        if(null == title){
            throw new GlobalException(ErrorCode.TITLE_NOT_FOUND);
        }
    }
    //최저가 확인
    public void checkLowPrice(Integer lowPrice){
        if(null == lowPrice){
            throw new GlobalException(ErrorCode.CONTENT_NOT_FOUND);
        }
    }
    //내용 확인
    public void checkContent(String content){
        if(null == content){
            throw new GlobalException(ErrorCode.CONTENT_NOT_FOUND);
        }
    }
}
