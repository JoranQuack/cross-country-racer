package seng201.team019.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;

/**
 * CSVReader is a utility class for reading CSV files from the resources folder
 * and parsing them into objects.
 */
public class CSVReader {

    // Parsers for Car and Upgrade objects
    public static Function<String[], Car> carParser = values -> new Car(values[0], Integer.parseInt(values[1]),
            Double.parseDouble(values[2]), Double.parseDouble(values[3]),
            Double.parseDouble(values[4]), Double.parseDouble(values[5]),
            Double.parseDouble(values[6]), Integer.parseInt(values[7]));

    public static Function<String[], Upgrade> upgradeParser = values -> new Upgrade(values[0],
            Double.parseDouble(values[1]),
            Double.parseDouble(values[2]), Double.parseDouble(values[3]),
            Double.parseDouble(values[4]), Integer.parseInt(values[5]),
            Double.parseDouble(values[6]), values[7]);

    /**
     * Reads a CSV file from the resources folder and parses it into a list of
     * objects using the provided parser function.
     *
     * @param resourcePath The path to the CSV file in the resources folder.
     * @param parser       A function that takes an array of strings (the values in
     *                     a row) and returns an object of type T.
     * @param <T>          The type of objects to be created from the CSV data.
     * @return A list of objects of type T created from the CSV data.
     */
    public <T> List<T> readCSV(String resourcePath, Function<String[], T> parser) {
        List<T> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(resourcePath)))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                T item = parser.apply(values);
                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
