package com.mini.auction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mini.auction.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.mini.auction.dto.request.ProductRequestDto.ProductRequestPostDto;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;

    private String path;

    @Column
    private String title;

    @Column
    private Integer lowPrice;

    @Column
    private String content;

    @Column
    private String winner;

    @Column
    private Integer highPrice;

    @Column
    private Boolean isSold;


    public Product(Member member, ProductRequestPostDto productRequestPostDto, String path) {
        this.member = member;
        this.title = productRequestPostDto.getTitle();
        this.lowPrice = productRequestPostDto.getLowPrice();
        this.content = productRequestPostDto.getContent();
        this.isSold = false;
        this.path = path;
        this.highPrice = 0;
        this.winner = null;
    }

    /**
     * 낙찰 시 soldProduct 메서드 호출
     */
    public void soldProduct() {
        this.isSold = true;
    }

    public void setWinner(Bid bid) {this.winner = bid.getMember().getUsername();}


    public void updateProduct(ProductRequestPostDto productRequestPostDto) {
        this.title = productRequestPostDto.getTitle();
        this.lowPrice = productRequestPostDto.getLowPrice();
        this.content = productRequestPostDto.getContent();
    }

    // 최고 입찰가 갱신
    public void updatePrice(Integer highPrice) {
        this.highPrice = highPrice;
    }
}
