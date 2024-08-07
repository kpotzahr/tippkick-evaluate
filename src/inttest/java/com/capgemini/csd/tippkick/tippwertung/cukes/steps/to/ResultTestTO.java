package com.capgemini.csd.tippkick.tippwertung.cukes.steps.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultTestTO {

    private int foreignteamScore;
    private int hometeamScore;
}
