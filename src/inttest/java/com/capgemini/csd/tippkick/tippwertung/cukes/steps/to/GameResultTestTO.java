package com.capgemini.csd.tippkick.tippwertung.cukes.steps.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResultTestTO {

    private long matchId;
    private ResultTestTO result;

    public static GameResultTestTO of(long matchId, String result) {
        String[] split = result.split(":");

        return builder()
                .matchId(matchId).
                        result(ResultTestTO.builder()
                                .hometeamScore(Integer.valueOf(split[0]))
                                .foreignteamScore(Integer.valueOf(split[1]))
                                .build())
                .build();
    }
}
