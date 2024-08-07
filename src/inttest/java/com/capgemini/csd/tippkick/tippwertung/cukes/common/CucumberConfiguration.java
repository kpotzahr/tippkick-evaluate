package com.capgemini.csd.tippkick.tippwertung.cukes.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.capgemini.csd.tippkick.tippwertung.cukes.*")
public class CucumberConfiguration {
    @Bean
    public KafkaSender matchKafkaSender(@Value("${spring.embedded.kafka.brokers:localhost:9092}") String kafkaUrl) {
        return new KafkaSender(kafkaUrl, "match-finished");
    }

    @Bean
    public KafkaSender tippKafkaSender(@Value("${spring.embedded.kafka.brokers:localhost:9092}") String kafkaUrl) {
        return new KafkaSender(kafkaUrl, "tipp");
    }
}
