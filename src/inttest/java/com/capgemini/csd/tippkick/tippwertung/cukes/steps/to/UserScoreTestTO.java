package com.capgemini.csd.tippkick.tippwertung.cukes.steps.to;

import lombok.Data;

@Data
public class UserScoreTestTO {
    private String tipper;
    private int score;
    private long userId;
}
