package com.mini.auction.entity;

import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.entity.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    // Product > product

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Comment(String comment, Member member, Product product) {
        this.comment = comment;
        this.member = member;
        this.product = product;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
