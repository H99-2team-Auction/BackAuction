package com.mini.auction.service;

import com.mini.auction.entity.Product;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

import static com.mini.auction.dto.ProductRequestDto.*;
import static com.mini.auction.dto.ProductResponseDto.*;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
//    private final CommentRepository commentRepository;

    public CommonProductResponseDto postProduct(ProductRequestPostDto productRequestPostDto) {
        Product savedProduct = new Product(productRequestPostDto);
        productRepository.save(savedProduct);

        return new CommonProductResponseDto(savedProduct);
    }

    public List<CommonProductResponseDto> findAllProducts() {
        List<Product> findProducts = productRepository.findAll();
        List<CommonProductResponseDto> productsResponseDto = new ArrayList<>();

        for (Product findProduct : findProducts) {
            productsResponseDto.add(new CommonProductResponseDto(findProduct));
        }

        return productsResponseDto;
    }

    public void findOneProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );

//        List<Comment> comments = commentRepository.findAllByProduct(findProduct);
        List<CommonProductResponseDto> productsResponseDto = new ArrayList<>();

//        for (Comment comment : comments) {
//            productsResponseDto.add(new Comm)
//        }
//
//        return
    }
}
