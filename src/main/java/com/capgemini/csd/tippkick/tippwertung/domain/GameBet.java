package com.capgemini.csd.tippkick.tippwertung.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GameBet {
    @Id
    @GeneratedValue
    private Long id;
    private long userId;
    private long matchId;

    @Embedded
    private Result result;

    public GameBet(long matchId, long userId) {
        this.matchId = matchId;
        this.userId = userId;
    }

    public UserScoreResultType compareToResult(Result actualResult) {
        if (isResultMatching(actualResult)) {
            return UserScoreResultType.EXACT;
        } else {
            if (isTendencyMatching(actualResult) && isDifferenceMatching(actualResult)) {
                return UserScoreResultType.TENDENCY_DIFFERENCE;
            } else if (isTendencyMatching(actualResult)) {
                return UserScoreResultType.TENDENCY;
            }
        }
        return UserScoreResultType.NONE;
    }

    private boolean isResultMatching(Result actualResult) {
        return actualResult.equals(this.result);
    }

    private boolean isTendencyMatching(Result actualResult) {
        if (result.hasForeignTeamWon() && actualResult.hasForeignTeamWon()) {
            return true;
        } else if (result.hasHomeTeamWon() && actualResult.hasHomeTeamWon()) {
            return true;
        } else if (result.isDraw() && actualResult.isDraw()) {
            return true;
        }
        return false;
    }

    private boolean isDifferenceMatching(Result actualResult) {
        return result.getDifference() == actualResult.getDifference();
    }
}
