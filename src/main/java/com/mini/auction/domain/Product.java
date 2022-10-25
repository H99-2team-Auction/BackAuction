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

//    private String igmage;

    private String title;
    private Integer lowPrice;
    private String content;

    /*
    입찰 최고가를 어디에 저장해둘까 고민하다가 Product 엔티티에 저장하는게 best 일거라는 생각이 들었습니다.
    처음에 bid 라는 테이블에 한 product 의 입찰 내역을 member 별로 새로 생성하는게 아니라, 최고입찰가와 member 를
    업데이트하는 방식으로 하려고 하다보니, 입찰에 참여한 member 의 리스트를 뽑는 방식이 까다로워졌습니다.

    그래서 업데이트 방식말고, 입찰에 참여할 때마다 생성하는 방식으로 바꾸기로 결정했습니다.
    근데 이런 방식으로 진행하면 bid 내의 최고 입찰가를 조회해오는 것이 어려워졌습니다. 입찰에 참여하는 사람이 생길 때마다
    bid 레포에서 해당 product 의 입찰에 참여한 명단을 모두 조회한 다음 그 중 가장 높은 입찰가를 제시한 member 를 조회해야
    찾을 수 있을 것 같았습니다. (다른 방법이 떠오르신다면 알려주시면 감사하겠습니다.)

     그래서 쉽게 최고 입찰가를 조회하는 방법이 뭘까 생각해 보다가 product 테이블에 highPrice 필드를 생성해서 입찰에 참여할
     때마다 업데이트를 해줘야겠다고 생각했습니다.
     */

    @Column
    private Integer highPrice;

    @Column
    private Boolean isSold;


    public Product(Member member, ProductRequestPostDto productRequestPostDto) {
        this.member = member;
        this.title = productRequestPostDto.getTitle();
        this.lowPrice = productRequestPostDto.getLowPrice();
        this.content = productRequestPostDto.getContent();
        this.isSold = false;
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

    // 최고 입찰가 갱신
    public void updatePrice(Integer highPrice) {
        this.highPrice = highPrice;
    }
}
