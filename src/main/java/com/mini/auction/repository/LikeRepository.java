package com.mini.auction.repository;

import com.mini.auction.domain.Like;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findAllByProductAndMember(Product findProduct, Member member);
    List<Like> findLikesByMember(Member member);

}
