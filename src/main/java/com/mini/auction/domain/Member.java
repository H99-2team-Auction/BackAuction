package com.mini.auction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mini.auction.dto.request.MemberRequestDto;
import com.mini.auction.domain.base.BaseTimeEntity;
import com.mini.auction.exception.CommentExceptions.NotAuthorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    public Member(MemberRequestDto memberReqDto) {
        this.username = memberReqDto.getUsername();
        this.password = memberReqDto.getPassword();
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password, this.password);
    }

    // 댓글 작성자인지 체크하는 메서드
    public void isAuthor(Comment comment) {
        if (!this.username.equals(comment.getMember().getUsername())) {
            throw new NotAuthorException(this.username);
        }
    }

    public void isAuthor(Product product) {
        if (!this.username.equals(product.getMember().getUsername())) {
            throw new NotAuthorException(this.username);
        }
    }
}
