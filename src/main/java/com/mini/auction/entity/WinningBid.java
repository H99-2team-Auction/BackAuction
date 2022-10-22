package com.mini.auction.entity;

import com.mini.auction.entity.base.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WinningBid extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

}
