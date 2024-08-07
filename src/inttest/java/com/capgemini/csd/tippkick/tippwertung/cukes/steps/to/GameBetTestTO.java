package com.capgemini.csd.tippkick.tippwertung.cukes.steps.to;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameBetTestTO {
    private static final String HOME_SCORE = "hometeamScore";
    private static final String FOREIGN_SCORE = "foreignteamScore";
    private static final String MATCH_ID = "matchId";
    private static final String OWNER_ID = "ownerId";

    private String tipper;
    private String ergebnis;

    public Object toEvent(long matchId, long ownerId) {
        String[] split = ergebnis.split(":");
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(HOME_SCORE, Integer.valueOf(split[0]));
        eventData.put(FOREIGN_SCORE, Integer.valueOf(split[1]));
        eventData.put(MATCH_ID, matchId);
        eventData.put(OWNER_ID, ownerId);

        return eventData;
    }

}
