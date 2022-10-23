package com.mini.auction.repository;

import com.mini.auction.entity.Bid;
import com.mini.auction.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

//    Optional<User> findByUsername(String username);

    List<Bid> findBidsByMember(Member member);
}
