package com.mini.auction.service;

import com.mini.auction.domain.Comment;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.exception.ErrorCode;
import com.mini.auction.exception.GlobalException;
import com.mini.auction.exception.bidException.AlreadyStartBidException;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.LikeRepository;
import com.mini.auction.repository.MemberRepository;
import com.mini.auction.repository.ProductRepository;
import com.mini.auction.utils.Check;
import com.mini.auction.utils.AmazonS3ResourceStorage;
import com.mini.auction.utils.MultipartUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final LikeRepository likeRepository;

    private final Check check;
    // 입찰 명단 조회 메서드(getParticipants) 호출하기 위해 의존성 주입
    private final BidService bidService;

    @Transactional
    public CommonProductResponseDto postProduct(Member member,
                                                ProductRequestPostDto productRequestPostDto,
                                                MultipartFile multipartFile) {
        log.info("=====================");
        log.info("member.getUsername() = {}", member.getUsername());
        log.info("=====================");
        //가입한 회원인지 확인
        if(null == check.isPresentMember(member.getUsername())){
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

        //제목 작성 확인
        check.checkTitle(productRequestPostDto.getTitle());
        //최저가 작성 확인
        check.checkLowPrice(productRequestPostDto.getLowPrice());
        //내용 작성 확인
        check.checkContent(productRequestPostDto.getContent());

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
     * >> 입찰자 수로 검색하려면 입찰자 수에 대한 필드가 상품에 추가되어야 할 듯
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
        Product findProduct = check.isExistedProduct(productId);
        List<Comment> comments = commentRepository.findAllByProductId(productId);
        List<CommentResponseDto> commentsResponseDto = new ArrayList<>();
        for (Comment comment : comments) {
            commentsResponseDto.add(new CommentResponseDto(comment));
        }

        List<String> participants = bidService.getBidParticipants(findProduct);
        /**
         * CommentResponseDto가 추가되면 List로 담아서 전달
         */
        return new ProductDetailResponseDto(findProduct, commentsResponseDto, participants);
    }

    /**
     * 게시물 삭제
     * @param productId
     * @return
     */
    @Transactional
    public String deleteProduct(Long productId) {
        Product findProduct = check.isExistedProduct(productId);
        // 입찰에 참여한 사람이 있을 경우 예외 처리
        isStartBid(findProduct);
        likeRepository.deleteAllByProduct(findProduct);
        commentRepository.deleteAllByProductId(productId);
        productRepository.delete(findProduct);

        return "게시물 삭제가 완료되었습니다.";
    }

    /**
     * 상품 수정
     */
    @Transactional
    public CommonProductResponseDto modifyProduct(Member member,
                                                  Long productId,
                                                  ProductRequestPostDto productRequestPostDto) {
        // 상품 존재 유무 확인
        Product findProduct = check.isExistedProduct(productId);

        String username = findProduct.getMember().getUsername();

        // 작성자 검증
        if(!member.getUsername().equals(username)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_USER);
        }
        /**
         * 입찰이 시작되면 작성자도 수정 불가능한지
         * 만약 그렇다면 isStartBid가 상품 존재 유무 확인 아래로 이동해야하는 것이 조금 더 좋을듯합니다
         */
        // 입찰 시작되면 수정 불가
        isStartBid(findProduct);

        findProduct.updateProduct(productRequestPostDto);

        return new CommonProductResponseDto(findProduct);

    }

    private void isStartBid(Product findProduct) {
        if (findProduct.getHighPrice() != 0) {
            throw new AlreadyStartBidException("입찰이 시작되었기 때문에 수정/삭제할 수 없습니다.");
        }
    }
}
