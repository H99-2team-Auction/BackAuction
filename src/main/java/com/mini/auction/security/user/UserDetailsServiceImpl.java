package com.mini.auction.security.user;

import com.mini.auction.entity.Member;
import com.mini.auction.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        Member member = memberRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("Account가 존재하지 않습니다.")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl(member);

        return userDetails;
    }
}
