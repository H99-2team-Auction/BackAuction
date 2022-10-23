package com.mini.auction.repository;

import com.mini.auction.entity.Bid;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

//    Optional<User> findByUsername(String username);

    List<Bid> findBidsByMember(Member member);

    Optional<Bid> findBidByProduct(Product product);
}
