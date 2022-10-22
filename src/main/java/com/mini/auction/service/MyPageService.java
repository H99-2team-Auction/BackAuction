package com.mini.auction.service;

import com.mini.auction.dto.ProductResponseDto;
import com.mini.auction.dto.response.ProductResponseDto;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final ProductRepository productRepository;

    public List<ProductResponseDto.CommonProductResponseDto> getSoldProductList(Member member) {
        // sql 로 db 에서 찾는게 효율적인지 서버에서 찾는게 효율적인지?
        // 왜 자꾸 자동완성이 isSold 라고 안뜨고 SoldIs 라고 뜸?
        List<Product> productList = productRepository.findProductsByMemberAndSoldIsTrue(member);

        // product 객체 responseDto 에 각각 넣기
        List<ProductResponseDto.CommonProductResponseDto> responseDtoList = new ArrayList<>();
        for (Product product : productList) {
            responseDtoList.add(new ProductResponseDto.CommonProductResponseDto(product));
        }
        return responseDtoList;
    }




}
