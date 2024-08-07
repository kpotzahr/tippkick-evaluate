package com.capgemini.csd.tippkick.tippwertung.adapter.message;

import com.capgemini.csd.tippkick.tippwertung.adapter.message.to.GameBetEvent;
import com.capgemini.csd.tippkick.tippwertung.adapter.message.to.GameResultEvent;
import com.capgemini.csd.tippkick.tippwertung.application.WertungService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class IncomingMessageHandler {

    private final WertungService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "tipp")
    public void listenFinalBet(String body) throws IOException {
        GameBetEvent message = objectMapper.readValue(body, GameBetEvent.class);
        log.info("Retrieve final bet for match {} from user {}", message.getMatchId(), message.getOwnerId());
        service.storeBet(message.getMatchId(), message.getOwnerId(), message.getHometeamScore(), message.getForeignteamScore());
    }

    @KafkaListener(topics = "match-finished")
    public void listenGameResult(String body) throws IOException {
        GameResultEvent message = objectMapper.readValue(body, GameResultEvent.class);
        log.info("Retrieve match {} end message", message.getMatchId());
        service.finalizeMatch(message.getMatchId(),
                message.getResult().getHometeamScore(),
                message.getResult().getForeignteamScore());
    }

}
