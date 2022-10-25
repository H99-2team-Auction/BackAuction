package com.mini.auction.service;

import com.mini.auction.domain.Comment;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.exception.bidException.AlreadyStartBidException;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.MemberRepository;
import com.mini.auction.repository.ProductRepository;
import com.mini.auction.util.Check;
import com.mini.auction.utils.AmazonS3ResourceStorage;
import com.mini.auction.utils.MultipartUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.mini.auction.dto.request.ProductRequestDto.ProductRequestPostDto;
import static com.mini.auction.dto.response.ProductResponseDto.CommonProductResponseDto;
import static com.mini.auction.dto.response.ProductResponseDto.ProductDetailResponseDto;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    private final Check check;

    @Transactional
    public CommonProductResponseDto postProduct(Member member,
                                                ProductRequestPostDto productRequestPostDto,
                                                MultipartFile multipartFile) {
        log.info("=====================");
        log.info("member.getUsername() = {}", member.getUsername());
        log.info("=====================");
//        //가입한 회원인지 확인
//        if(null == check.isPresentMember(member.getUsername())){
//            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
//        }
        memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Member 정보를 찾을 수 없습니다.")
        );
        //제목 확인
//        check.checkTitle(productRequestPostDto.getTitle());
        //최저가 확인
//        check.checkLowPrice(productRequestPostDto.getLowPrice());
        //내용확인
//        check.checkContent(productRequestPostDto.getContent());

        String path = createPath(multipartFile);
        log.info("path = {}", path);
        log.info("=====================");
        amazonS3ResourceStorage.store(path, multipartFile);

        Product savedProduct = new Product(member, productRequestPostDto, path);
        productRepository.save(savedProduct);

        return new CommonProductResponseDto(savedProduct);
    }

    private String createPath(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());

        return MultipartUtil.createPath(fileId, format);

    }

    /**
     * 상품 전체 검색
     * 입찰자 수, 수정일자 내림차순
     */
    public List<CommonProductResponseDto> findAllProducts() {
        List<Product> findProducts = productRepository.findByIsSoldFalseOrderByModifiedAtDesc();
        List<CommonProductResponseDto> productsResponseDto = new ArrayList<>();

        for (Product findProduct : findProducts) {
            productsResponseDto.add(new CommonProductResponseDto(findProduct));
        }
        return productsResponseDto;
    }

    public ProductDetailResponseDto findOneProduct(Long productId) {
        Product findProduct = isExistedProduct(productId);
        List<Comment> comments = commentRepository.findAllByProductId(productId);
        List<CommentResponseDto> commentsResponseDto = new ArrayList<>();
        for (Comment comment : comments) {
            commentsResponseDto.add(new CommentResponseDto(comment));
        }
        /**
         * CommentResponseDto가 추가되면 List로 담아서 전달
         */
        return new ProductDetailResponseDto(findProduct, commentsResponseDto);
    }

    /**
     * 게시물 삭제
     * @param productId
     * @return
     */
    @Transactional
    public String deleteProduct(Long productId) {
        Product findProduct = isExistedProduct(productId);
        // 입찰에 참여한 사람이 있을 경우 예외 처리
        isStartBid(findProduct);
        commentRepository.deleteAllByProductId(productId);
        productRepository.delete(findProduct);
        return "게시물이 삭제되었습니다.";
    }

    /**
     * 상품 수정
     *
     * @return
     */
    @Transactional
    public CommonProductResponseDto modifyProduct(Member member,
                                                  Long productId,
                                                  ProductRequestPostDto productRequestPostDto) {
        // 상품 존재 유무 확인
        Product findProduct = isExistedProduct(productId);
        // 작성자 검증
        member.isAuthor(findProduct);
        // 입찰에 참여한 사람이 있을 경우 예외 처리
        isStartBid(findProduct);


        findProduct.updateProduct(productRequestPostDto);

        return new CommonProductResponseDto(findProduct);
    }

    /**
     * Product 존재 유무 확인
     */
    private Product isExistedProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품은 존재하지 않습니다.")
        );
    }

    private void isStartBid(Product findProduct) {
        if (findProduct.getHighPrice() != 0) {
            throw new AlreadyStartBidException("입찰이 시작되었기 때문에 수정/삭제할 수 없습니다.");
        }
    }

}
