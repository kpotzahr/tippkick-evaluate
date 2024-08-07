package com.capgemini.csd.tippkick.tippwertung.application;

import com.capgemini.csd.tippkick.tippwertung.domain.GameBet;
import com.capgemini.csd.tippkick.tippwertung.domain.GameBetRepository;
import com.capgemini.csd.tippkick.tippwertung.domain.UserScore;
import com.capgemini.csd.tippkick.tippwertung.domain.UserScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.capgemini.csd.tippkick.tippwertung.domain.Result.result;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WertungServiceTest {

    private static final long USER_ID = 2L;
    private static final long MATCH_ID = 1L;
    private static final int HOMETEAM_SCORE = 3;
    private static final int FOREIGNTEAM_SCORE = 4;
    private static final int EXPECTED_SCORE_AFTER_WIN = 3;
    private static final int EXPECTED_SCORE_NO_WIN = 0;
    private static final int EXPECTED_SCORE_TENDENCY_WIN = 1;
    private static final int EXPECTED_SCORE_TENDENCY_AND_DIFFERENCE_WIN = 2;

    @InjectMocks
    private WertungService wertungService;
    @Mock
    private GameBetRepository gameBetRepository;
    @Mock
    private UserScoreRepository userScoreRepository;

    @Test
    void shouldStoreBet() {
        // given
        when(gameBetRepository.findByMatchIdAndUserId(MATCH_ID, USER_ID)).thenReturn(Optional.empty());
        // when
        wertungService.storeBet(MATCH_ID, USER_ID, HOMETEAM_SCORE, FOREIGNTEAM_SCORE);
        // then
        verify(gameBetRepository).save(any(GameBet.class));
    }

    @Test
    void shouldCalculateWin() {
        // given
        GameBet gameBet = new GameBet();
        gameBet.setUserId(USER_ID);
        gameBet.setMatchId(MATCH_ID);
        gameBet.setResult(result(HOMETEAM_SCORE, FOREIGNTEAM_SCORE));
        when(gameBetRepository.findByMatchId(anyLong())).thenReturn(singletonList(gameBet));
        UserScore userScore = new UserScore();
        when(userScoreRepository.findById(anyLong())).thenReturn(Optional.of(userScore));
        // when
        wertungService.finalizeMatch(MATCH_ID, HOMETEAM_SCORE, FOREIGNTEAM_SCORE);
        // then
        verify(gameBetRepository).findByMatchId(MATCH_ID);
        verify(userScoreRepository).findById(USER_ID);
        verify(userScoreRepository).save(any());
        assertThat(userScore.getScore()).isEqualTo(EXPECTED_SCORE_AFTER_WIN);
    }

    @Test
    void shouldCalculateTendencyWin() {
        // given
        GameBet gameBet = new GameBet();
        gameBet.setUserId(USER_ID);
        gameBet.setMatchId(MATCH_ID);
        gameBet.setResult(result(HOMETEAM_SCORE, FOREIGNTEAM_SCORE + 1));
        when(gameBetRepository.findByMatchId(anyLong())).thenReturn(singletonList(gameBet));
        UserScore userScore = new UserScore();
        when(userScoreRepository.findById(anyLong())).thenReturn(Optional.of(userScore));
        // when
        wertungService.finalizeMatch(MATCH_ID, HOMETEAM_SCORE, FOREIGNTEAM_SCORE);
        // then
        verify(gameBetRepository).findByMatchId(MATCH_ID);
        verify(userScoreRepository).findById(USER_ID);
        verify(userScoreRepository).save(any());
        assertThat(userScore.getScore()).isEqualTo(EXPECTED_SCORE_TENDENCY_WIN);
    }

    @Test
    void shouldCalculateTendencyAndDifferenceWin() {
        // given
        GameBet gameBet = new GameBet();
        gameBet.setUserId(USER_ID);
        gameBet.setMatchId(MATCH_ID);
        gameBet.setResult(result(HOMETEAM_SCORE + 1, FOREIGNTEAM_SCORE + 1));
        when(gameBetRepository.findByMatchId(anyLong())).thenReturn(singletonList(gameBet));
        UserScore userScore = new UserScore();
        when(userScoreRepository.findById(anyLong())).thenReturn(Optional.of(userScore));
        // when
        wertungService.finalizeMatch(MATCH_ID, HOMETEAM_SCORE, FOREIGNTEAM_SCORE);
        // then
        verify(gameBetRepository).findByMatchId(MATCH_ID);
        verify(userScoreRepository).findById(USER_ID);
        verify(userScoreRepository).save(any());
        assertThat(userScore.getScore()).isEqualTo(EXPECTED_SCORE_TENDENCY_AND_DIFFERENCE_WIN);
    }

    @Test
    void shouldCalculateNoWin() {
        // given
        GameBet gameBet = new GameBet();
        gameBet.setUserId(USER_ID);
        gameBet.setMatchId(MATCH_ID);
        gameBet.setResult(result(HOMETEAM_SCORE + 1, FOREIGNTEAM_SCORE));
        when(gameBetRepository.findByMatchId(anyLong())).thenReturn(singletonList(gameBet));
        UserScore userScore = new UserScore();
        when(userScoreRepository.findById(anyLong())).thenReturn(Optional.of(userScore));
        // when
        wertungService.finalizeMatch(MATCH_ID, HOMETEAM_SCORE, FOREIGNTEAM_SCORE);
        // then
        verify(gameBetRepository).findByMatchId(MATCH_ID);
        verify(userScoreRepository).findById(USER_ID);
        verify(userScoreRepository).save(any());
        assertThat(userScore.getScore()).isEqualTo(EXPECTED_SCORE_NO_WIN);
    }
}
