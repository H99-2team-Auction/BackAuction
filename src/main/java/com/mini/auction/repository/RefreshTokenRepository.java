package com.mini.auction.repository;

import com.mini.auction.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberUsername(String username);
    void deleteByMemberUsername(String username);
}
