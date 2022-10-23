package com.mini.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BidRequestDto {

    // requestDto 내에서 lowprice 와 비교해서 vaild 할 수 있는지??????????????????
    @NotBlank(message = "입찰가를 입력해 주세요")
    private Integer biddingPrice;

}
