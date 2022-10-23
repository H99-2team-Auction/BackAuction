package com.mini.auction.entity;

import com.mini.auction.entity.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.mini.auction.dto.request.ProductRequestDto.ProductRequestPostDto;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    private String image;

    private String title;
    private Integer lowPrice;
    private String content;

    @Column(columnDefinition = "boolean default false")
    private boolean isSold;


    public Product(Member member, ProductRequestPostDto productRequestPostDto) {
        this.member = member;

        title = productRequestPostDto.getTitle();
        lowPrice = productRequestPostDto.getLowPrice();
        content = productRequestPostDto.getContent();
    }

    /**
     * 낙찰 시 soldProduct 메서드 호출
     */
    public void soldProduct() {
        this.isSold = true;
    }


    public void updateProduct(ProductRequestPostDto productRequestPostDto) {
        this.title = productRequestPostDto.getTitle();
        this.lowPrice = productRequestPostDto.getLowPrice();
        this.content = productRequestPostDto.getContent();
    }
}
