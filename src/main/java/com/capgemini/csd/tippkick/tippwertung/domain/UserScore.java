package com.capgemini.csd.tippkick.tippwertung.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "userId")
public class UserScore {
    private static final int POINTS_CORRECT_RESULT = 3;
    private static final int POINTS_TENDENCY_DIFFERENCE_RESULT = 2;
    private static final int POINTS_TENDENCY = 1;

    @Id
    private long userId;
    private int score;

    public UserScore(long userId) {
        this.userId = userId;
        this.score = 0;
    }

    public void registerBet(UserScoreResultType type) {
        if (UserScoreResultType.EXACT.equals(type)) {
            // exact win
            score = score + POINTS_CORRECT_RESULT;
        } else {
            if (UserScoreResultType.TENDENCY_DIFFERENCE.equals(type)) {
                // tendency + difference
                score = score + POINTS_TENDENCY_DIFFERENCE_RESULT;
            } else if (UserScoreResultType.TENDENCY.equals(type)) {
                // only difference
                score = score + POINTS_TENDENCY;
            }
        }
    }
}
