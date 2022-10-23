package com.mini.auction.repository;

import com.mini.auction.entity.Like;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findAllByProductAndMember(Product findProduct, Member member);
    List<Like> findLikesByMember(Member member);

}
