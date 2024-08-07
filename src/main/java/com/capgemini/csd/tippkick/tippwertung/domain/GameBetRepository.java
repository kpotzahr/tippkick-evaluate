package com.capgemini.csd.tippkick.tippwertung.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameBetRepository extends JpaRepository<GameBet, Long> {
    List<GameBet> findByMatchId(Long matchId);

    Optional<GameBet> findByMatchIdAndUserId(Long matchId, Long userId);
}
