package seng201.team019.services;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Race;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class JsonRaceDeserializerTest {

    JsonRaceDeserializer jsonRaceDeserializer;

    @Mock
    private GameEnvironment gameEnvironment;

    @Mock
    private Car car1;

    @BeforeEach
    public void setUp() {

        jsonRaceDeserializer = new JsonRaceDeserializer();
    }

    @Test
    public void readJsonRaceFile() {
        String testfile = "/data/races/complete_race_data.json";
        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile)) {
            Assertions.assertNotNull(is);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void readJsonRaceFileMissingFileTest() {
        String testfile = "/data/races/randomRaceTestFile.json"; // This Doesnt exist
        Assertions.assertThrows(IOException.class, () -> {
            jsonRaceDeserializer.readJsonRaceFile(testfile);
        });
    }

    @Test
    public void readRaceFromInputStreamTest() throws IOException {
        String testfile = "/data/races/complete_race_data.json";
        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile)) {
            Race race = jsonRaceDeserializer.readRaceFromInputStream(is);
            Assertions.assertNotNull(race);
            Assertions.assertEquals(3, race.getNumOfOpponents());
            Assertions.assertEquals(100f, race.getPrizeMoney());
            Assertions.assertEquals(2, race.getRoutes().size());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    public void readJsonRaceFileEmptyFileTest() throws IOException {
        String testfile = "/data/races/empty_race_data.json"; // This is empty

        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile);) {
            Assertions.assertThrows(IOException.class, () -> {
                jsonRaceDeserializer.readRaceFromInputStream(is);
            });
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    @DisplayName("Test Json file with incomplete Race data")
    public void readJsonRaceFileIncompleteFileTest1() throws IOException {
        String testfile = "/data/races/missing_fields_race_data.json"; // this is missing race data

        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile);) {
            Assertions.assertThrows(IllegalStateException.class, () -> {
                jsonRaceDeserializer.readRaceFromInputStream(is);
            });
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    @DisplayName("Test Json file with incomplete Route data")
    public void readJsonRaceFileIncompleteFileTest2() throws IOException {
        String testfile = "/data/races/missing_route_fields_race_data.json"; // this is missing route data

        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile);) {
            Assertions.assertThrows(MismatchedInputException.class, () -> {
                jsonRaceDeserializer.readRaceFromInputStream(is);
            });
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    @DisplayName("Test Json file with Additional Race data")
    public void readJsonRaceFileAdditionalRaceFieldsTest1() throws IOException {
        String testfile = "/data/races/additional_fields_race_data.json"; // this has additional fields

        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile);) {
            Assertions.assertThrows(UnrecognizedPropertyException.class, () -> {
                jsonRaceDeserializer.readRaceFromInputStream(is);
            });
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Test
    @DisplayName("Test Json file with Additional Route data")
    public void readJsonRaceFileAdditionalRaceFieldsTest2() throws IOException {
        String testfile = "/data/races/additional_route_fields_race_data.json";

        try (InputStream is = jsonRaceDeserializer.readJsonRaceFile(testfile);) {
            Assertions.assertThrows(MismatchedInputException.class, () -> {
                jsonRaceDeserializer.readRaceFromInputStream(is);
            });
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
