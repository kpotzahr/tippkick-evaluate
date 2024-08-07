package com.capgemini.csd.tippkick.tippwertung.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Result {
    private int hometeamScore;
    private int foreignteamScore;

    public static Result result(int hometeamScore, int foreignteamScore) {
        return new Result(hometeamScore, foreignteamScore);
    }

    boolean hasForeignTeamWon() {
        return hometeamScore < foreignteamScore;
    }

    boolean hasHomeTeamWon() {
        return hometeamScore > foreignteamScore;
    }

    boolean isDraw() {
        return hometeamScore == foreignteamScore;
    }

    int getDifference() {
        return Math.abs(hometeamScore - foreignteamScore);
    }
}
