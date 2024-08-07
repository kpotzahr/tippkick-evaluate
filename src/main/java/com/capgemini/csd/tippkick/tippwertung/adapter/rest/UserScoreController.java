package com.capgemini.csd.tippkick.tippwertung.adapter.rest;

import com.capgemini.csd.tippkick.tippwertung.adapter.rest.to.UserScoreTO;
import com.capgemini.csd.tippkick.tippwertung.application.SortOrder;
import com.capgemini.csd.tippkick.tippwertung.application.WertungService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserScoreController {
    private final WertungService wertungService;

    @ApiOperation("Lists current user scores. If sort=asc/desc is set, the result is sorted by score.")
    @GetMapping("/score")
    public List<UserScoreTO> listUserScores(@RequestParam(name = "sortOrder", defaultValue = "desc") SortOrder sortOrder) {
        return wertungService.listUserScores(sortOrder);
    }
}
