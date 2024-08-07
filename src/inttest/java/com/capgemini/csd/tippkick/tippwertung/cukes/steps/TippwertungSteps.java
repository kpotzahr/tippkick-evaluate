package com.capgemini.csd.tippkick.tippwertung.cukes.steps;

import com.capgemini.csd.tippkick.tippwertung.cukes.common.DbAccess;
import com.capgemini.csd.tippkick.tippwertung.cukes.common.KafkaSender;
import com.capgemini.csd.tippkick.tippwertung.cukes.steps.to.GameBetTestTO;
import com.capgemini.csd.tippkick.tippwertung.cukes.steps.to.GameResultTestTO;
import com.capgemini.csd.tippkick.tippwertung.cukes.steps.to.UserScoreTestTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class TippwertungSteps {
    private final KafkaSender matchKafkaSender;
    private final KafkaSender tippKafkaSender;
    private final DbAccess dbAccess;
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${application.url:http://localhost:7082}")
    private String applicationUrl;

    private long currentMatchId = 123;
    private long currentUserId = 1887;
    private Map<String, Long> userNameToId = new HashMap<>();

    @Given("^Folgende Tipps sind für ein Spiel eingegangen:$")
    public void gameBetsRegistered(List<GameBetTestTO> bets) {
        long currentId = 1;
        for (GameBetTestTO bet : bets) {
            long userId = currentId++;
            userNameToId.put(bet.getTipper(), userId);
            tippKafkaSender.sendMessage(bet.toEvent(currentMatchId, userId), currentMatchId);
        }
    }

    @Given("^ich habe (\\d+) Punkte$")
    public void givenScore(int score) throws SQLException {
        dbAccess.insertUserScore(currentUserId, score);

    }

    @When("^das Spiel mit Ergebnis \"([^\"]*)\" endet$")
    public void gameHasFinalResult(String result) {
        matchKafkaSender.sendMessage(GameResultTestTO.of(currentMatchId, result), currentMatchId);
    }

    @When("^ich tippe \"([^\"]*)\"$")
    public void ichTippe(String result) {
        GameBetTestTO bet = new GameBetTestTO();
        bet.setErgebnis(result);
        tippKafkaSender.sendMessage(bet.toEvent(currentMatchId, currentUserId), currentMatchId);
    }

    @Then("^es wurden folgende Scores berechnet:$")
    public void checkScores(List<UserScoreTestTO> expectedScores) throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(applicationUrl + "/score", String.class);
        List<UserScoreTestTO> acualUserScores = objectMapper.readValue(response.getBody(),
                new TypeReference<List<UserScoreTestTO>>() {
                });

        Map<Long, Integer> userId2score = new HashMap<>();
        for (UserScoreTestTO score : acualUserScores) {
            userId2score.put(score.getUserId(), score.getScore());
        }

        for (UserScoreTestTO expectedScore : expectedScores) {
            Integer actualScore = userId2score.get(userNameToId.get(expectedScore.getTipper()));
            assertThat(actualScore).isEqualTo(expectedScore.getScore());
        }
    }

    @Then("^mein Punktestand wurde mit (\\d+) berechnet$")
    public void meinPunktestandWurdeMitBerechnet(int expectedScore) throws Throwable {
        ResponseEntity<String> response = restTemplate.getForEntity(applicationUrl + "/score", String.class);
        List<UserScoreTestTO> acualUserScores = objectMapper.readValue(response.getBody(),
                new TypeReference<List<UserScoreTestTO>>() {
                });
        UserScoreTestTO myScore = acualUserScores.stream().filter(score -> score.getUserId() == currentUserId).findFirst().get();
        assertThat(myScore.getScore()).isEqualTo(expectedScore);
    }
}
