package com.capgemini.csd.tippkick.tippwertung.cukes.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Component
public class DbAccess {
    private static final String[] TABLES = {"GAME_BET", "USER_SCORE"};

    @Value("${spring.datasource.url:jdbc:h2:tcp://localhost:7092/file:~/tippwertung}")
    private String dbUrl;

    @Value("${spring.datasource.username:sa}")
    private String dbUser;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    void cleanupData() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.dbUser);
        connectionProps.put("password", this.dbPassword);

        try (Connection connection = DriverManager.getConnection(dbUrl, connectionProps);
             Statement stmt = connection.createStatement()) {
            for (String table : TABLES) {
                stmt.executeUpdate("delete from " + table);
            }
        }
    }

    public void insertUserScore(long userId, int score) throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", this.dbUser);
        connectionProps.put("password", this.dbPassword);

        try (Connection connection = DriverManager.getConnection(dbUrl, connectionProps);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("insert into USER_SCORE (USER_ID, SCORE) values (" + userId + ", " + score + ")");
        }
    }

}
