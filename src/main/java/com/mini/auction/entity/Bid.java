package com.mini.auction.entity;

import com.mini.auction.dto.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor

public class Bid extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Bid(Product product) {
        this.product = product;
    }
}
