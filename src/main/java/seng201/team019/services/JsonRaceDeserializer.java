package seng201.team019.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

import java.io.IOException;
import java.io.InputStream;

public class JsonRaceDeserializer {
    ObjectMapper objectMapper;
    GameEnvironment gameEnvironment;

    public JsonRaceDeserializer(GameEnvironment gameEnvironment) {
        objectMapper = new ObjectMapper();
        this.gameEnvironment = gameEnvironment;
    }

    public Race readJsonRaceString(InputStream jsonFile) throws IOException {
        Race.Builder raceBuilder = objectMapper.readValue(jsonFile, Race.Builder.class);
        raceBuilder.withGameEnvironment(gameEnvironment);
        return raceBuilder.build();
    }
}
