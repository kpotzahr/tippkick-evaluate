package com.capgemini.csd.tippkick.tippwertung.application;

import com.capgemini.csd.tippkick.tippwertung.adapter.rest.to.UserScoreTO;
import com.capgemini.csd.tippkick.tippwertung.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.capgemini.csd.tippkick.tippwertung.domain.Result.result;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction;

@Service
@RequiredArgsConstructor
public class WertungService {

    private final GameBetRepository gameBetRepository;
    private final UserScoreRepository userScoreRepository;

    @Transactional
    public void storeBet(Long matchId, Long userId, int hometeamScore, int foreignteamScore) {
        GameBet gameBet = gameBetRepository.findByMatchIdAndUserId(matchId, userId).orElse(new GameBet(matchId, userId));
        gameBet.setResult(result(hometeamScore, foreignteamScore));
        gameBetRepository.save(gameBet);
    }

    @Transactional
    public void finalizeMatch(Long matchId, int hometeamScore, int foreignteamScore) {
        gameBetRepository
                .findByMatchId(matchId)
                .forEach(bet -> calculate(bet, result(hometeamScore, foreignteamScore)));
    }

    public List<UserScoreTO> listUserScores(SortOrder sortOrder) {
        return getAllSorted(sortOrder)
                .stream()
                .map(this::mapToUserScoreTO)
                .collect(toList());
    }

    private List<UserScore> getAllSorted(SortOrder sortOrder) {
        Direction direction = mapToDirection(sortOrder);
        return userScoreRepository.findAll(JpaSort.by(direction, "score"));
    }

    private Direction mapToDirection(SortOrder sortOrder) {
        if (SortOrder.asc.equals(sortOrder)) {
            return Direction.ASC;
        } else if (SortOrder.desc.equals(sortOrder)) {
            return Direction.DESC;
        } else {
            return Sort.DEFAULT_DIRECTION;
        }
    }

    private UserScoreTO mapToUserScoreTO(UserScore userScore) {
        return new UserScoreTO(userScore.getUserId(), userScore.getScore());
    }

    private void calculate(GameBet storedBet, Result actualResult) {
        UserScore userScore = userScoreRepository
                .findById(storedBet.getUserId())
                .orElse(new UserScore(storedBet.getUserId()));

        UserScoreResultType userScoreResultType = storedBet.compareToResult(actualResult);
        userScore.registerBet(userScoreResultType);

        userScoreRepository.save(userScore);
    }
}
