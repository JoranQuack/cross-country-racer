package seng201.team019.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import seng201.team019.models.Race;

import java.io.IOException;
import java.io.InputStream;

/**
 * JsonRaceDeserializer is a utility class for reading JSON files from the
 * resources folder and building them into race objects using the race builders.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class JsonRaceDeserializer {
    /**
     * ObjectMapper is a Jackson library class that is used to convert JSON
     * strings to Java objects and vice versa.
     */
    ObjectMapper objectMapper;

    /**
     * Constructor for the JsonRaceDeserializer class.
     */
    public JsonRaceDeserializer() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Reads a JSON file from the resources folder and parses it.
     * @param jsonFileName the file name
     * @return the {@link InputStream} representation of that file
     * @throws IOException if an error occurs.
     * @throws NullPointerException if the file name is invalid
     */
    public InputStream readJsonRaceFile(String jsonFileName) throws IOException, NullPointerException {
        InputStream is = getClass().getResourceAsStream(jsonFileName);
        if (is == null) {
            throw new IOException("Resource not found: " + jsonFileName);
        }
        return is;
    }

    /**
     * Reads a JSON file from the input stream and parses it
     * @param jsonFile an input stream of the jsonfile
     * @return a {@link Race} instance with attributes set from json
     * @throws IOException when input stream is invalid
     */
    public Race readRaceFromInputStream(InputStream jsonFile) throws IOException {
        Race.Builder raceBuilder = objectMapper.readValue(jsonFile, Race.Builder.class);
        return raceBuilder.build();
    }
}
