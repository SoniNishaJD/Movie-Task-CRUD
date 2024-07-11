package com.real.matcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReaderUtil {

    public static List<Movie> readMovies(CsvStream csvStream) throws Exception {
        List<String[]> records = readCSV(csvStream);
        List<Movie> movies = new ArrayList<>();
        for (String[] record : records) {
            int id = Integer.parseInt(record[0]);
            String title = record[1];
            int year = Integer.parseInt(record[2]);
            movies.add(new Movie(id, title, year));
        }
        return movies;
    }

    public static void addActorsAndDirectors(List<Movie> movies, CsvStream csvStream) throws Exception {
        List<String[]> records = readCSV(csvStream);
        for (String[] record : records) {
            int movieId = Integer.parseInt(record[0]);
            String name = record[1];
            String role = record[2];

            movies.stream()
                    .filter(movie -> movie.getId() == movieId)
                    .findFirst()
                    .ifPresent(movie -> {
                        if ("cast".equalsIgnoreCase(role)) {
                            movie.addActor(name);
                        } else if ("director".equalsIgnoreCase(role)) {
                            movie.setDirector(name);
                        }
                    });
        }
    }

    public static List<ProviderMovie> readProviderMovies(CsvStream csvStream) throws Exception {
        List<String[]> records = readCSV(csvStream);
        List<ProviderMovie> providerMovies = new ArrayList<>();
        for (String[] record : records) {
            String mediaId = record[0];
            String title = record[1];
            String releaseDate = record[2];
            String mediaType = record[3];
            String actors = record[4];
            String director = record[5];
            String xboxLiveURL = record[6];
            providerMovies.add(new ProviderMovie(mediaId, title, releaseDate, mediaType, actors, director, xboxLiveURL));
        }
        return providerMovies;
    }

    private static List<String[]> readCSV(CsvStream csvStream) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvStream.getStream()))) {
            return br.lines()
                    .skip(1) // Skip the header
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        }
    }
}
