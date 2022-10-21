package com.mini.auction.service;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.entity.Comment;
import com.mini.auction.entity.Product;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mini.auction.dto.request.ProductRequestDto.*;
import static com.mini.auction.dto.response.ProductResponseDto.*;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

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
        Product findProduct = isExistedProduct(productId);

        List<Comment> comments = commentRepository.findCommentsByProduct(findProduct);
        /**
         * CommentResponseDto가 추가되면 List로 담아서 전달
         */
//        List<CommentResponseDto> commentsResponseDto = new ArrayList<>();
//        for (Comment comment : comments) {
//            commentsResponseDto.add(new Comm)
//        }
//        return
    }

    public ResponseDto<String> deleteProduct(Long productId) {
        isExistedProduct(productId);

        productRepository.deleteById(productId);

        return ResponseDto.success("게시물 삭제가 완료되었습니다.");
    }

    public void auctionedOff(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 상품입니다.")
        );
        findProduct.successBid();

        // Member에 findProduct를 넣어준다

    }

    /**
     * Product 존재 유무 확인
     */
    private Product isExistedProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );
        return findProduct;
    }




}
