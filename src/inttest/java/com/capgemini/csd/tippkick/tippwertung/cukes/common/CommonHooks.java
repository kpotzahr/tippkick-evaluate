package com.capgemini.csd.tippkick.tippwertung.cukes.common;

import com.capgemini.csd.tippkick.tippwertung.TippwertungApplication;
import cucumber.api.java.Before;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

@RequiredArgsConstructor
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CommonHooks {
    private static boolean isStarted = false;

    @Value("${application.url:http://localhost}:${server.port:7082}")
    private String applicationUrl;

    private final DbAccess dbAccess;

    @Before("@cleanData")
    public void cleanupData() throws SQLException {
        dbAccess.cleanupData();
    }

    @Before(order = 10)
    public void startApp() {
        if (!isStarted) {
            try {
                // check if application is started
                TestRestTemplate restTemplate = new TestRestTemplate();
                restTemplate.getForEntity(applicationUrl + "/swagger-ui.html", Void.class);
            } catch (Exception exc) {
                TippwertungApplication.main(new String[]{});
            }
            isStarted = true;
        }
    }
}
