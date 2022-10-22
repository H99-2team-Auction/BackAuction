package com.mini.auction.entity;

import com.mini.auction.dto.request.ProductRequestDto;
import com.mini.auction.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static com.mini.auction.dto.request.ProductRequestDto.*;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

//    private String image;

    private String title;
    private Integer lowprice;
    private String content;

    @ColumnDefault("false")
    private boolean isSold;

    public Product(Member member, ProductRequestPostDto productRequestPostDto) {
        this.member = member;

        title = productRequestPostDto.getTitle();
        lowprice = productRequestPostDto.getLowprice();
        content = productRequestPostDto.getContent();
    }

    public void successBid() {
        isSold = true;
    }

    public void updateProduct(ProductRequestPostDto productRequestPostDto) {
        this.title = productRequestPostDto.getTitle();
        this.lowprice = productRequestPostDto.getLowprice();
        this.content = productRequestPostDto.getContent();
    }
}
