package com.capgemini.csd.tippkick.tippwertung.domain;

/**
 * Models types of bets on results.
 */
public enum UserScoreResultType {
    /**
     * Excat bet, i.e. the bet and the match result match.
     */
    EXACT,

    /**
     * Tendency bet, i.e. the bet matches the winning team.
     */
    TENDENCY,

    /**
     * Difference bet, i.e. the bet matches the difference between goals.
     */
    TENDENCY_DIFFERENCE,

    /**
     * Bet did not match.
     */
    NONE
}
