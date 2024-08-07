package com.capgemini.csd.tippkick.tippwertung.adapter;

import com.capgemini.csd.tippkick.tippwertung.adapter.rest.UserScoreController;
import com.capgemini.csd.tippkick.tippwertung.adapter.rest.to.UserScoreTO;
import com.capgemini.csd.tippkick.tippwertung.application.SortOrder;
import com.capgemini.csd.tippkick.tippwertung.application.WertungService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserScoreControllerTest {

    @InjectMocks
    private UserScoreController userScoreController;
    @Mock
    private WertungService wertungService;

    @Test
    void shouldListUserScores() {
        // given
        UserScoreTO userScore = new UserScoreTO(123L, 3);
        when(wertungService.listUserScores(SortOrder.asc)).thenReturn(Collections.singletonList(userScore));
        // when
        List<UserScoreTO> userScoreTOS = userScoreController.listUserScores(SortOrder.asc);
        // then
        assertThat(userScoreTOS).hasSize(1);
    }
}