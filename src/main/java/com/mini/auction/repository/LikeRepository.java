package com.mini.auction.repository;

import com.mini.auction.entity.Like;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findAllByProductAndMember(Product findProduct, Member member);
}
