package com.mini.auction.domain;

import com.mini.auction.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WinningBid extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

}
