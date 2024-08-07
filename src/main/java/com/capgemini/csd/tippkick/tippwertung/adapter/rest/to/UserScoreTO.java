package com.capgemini.csd.tippkick.tippwertung.adapter.rest.to;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserScoreTO {
    private long userId;
    private int score;
}
