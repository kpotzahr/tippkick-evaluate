package com.capgemini.csd.tippkick.tippwertung.adapter.message.to;

import lombok.Data;

@Data
public class GameResultEvent {
    private long matchId;
    private Result result;
}
