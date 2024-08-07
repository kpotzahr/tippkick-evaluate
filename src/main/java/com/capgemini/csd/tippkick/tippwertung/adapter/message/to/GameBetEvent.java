package com.capgemini.csd.tippkick.tippwertung.adapter.message.to;

import lombok.Data;

@Data
public class GameBetEvent {
    private long matchId;
    private long ownerId;
    private int hometeamScore;
    private int foreignteamScore;
}
