package com.mini.auction.repository;

import com.mini.auction.domain.Bid;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

//    Optional<User> findByUsername(String username);

    List<Bid> findBidsByMember(Member member);


    List<Bid> findBidsByProduct(Product product);

    Bid findBidByProductAndHighPrice(Product product, Integer highPrice);
}
