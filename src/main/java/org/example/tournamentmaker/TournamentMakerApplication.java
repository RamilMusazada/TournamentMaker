package org.example.tournamentmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TournamentMakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournamentMakerApplication.class, args);
    }

}
