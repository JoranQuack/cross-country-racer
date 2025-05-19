package seng201.team019.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import seng201.team019.models.Race;

import java.io.IOException;
import java.io.InputStream;

public class JsonRaceDeserializer {
    ObjectMapper objectMapper;

    public JsonRaceDeserializer() {
        objectMapper = new ObjectMapper();
    }

    public InputStream readJsonRaceFile(String jsonFileName) throws IOException, NullPointerException {
        InputStream is = getClass().getResourceAsStream(jsonFileName);
        if (is == null) {
            throw new IOException("Resource not found: " + jsonFileName);
        }
        return is;
    }

    public Race readRaceFromInputStream(InputStream jsonFile) throws IOException {
        Race.Builder raceBuilder = objectMapper.readValue(jsonFile, Race.Builder.class);
        return raceBuilder.build();
    }
}
