package com.real.matcher;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatcherImpl implements Matcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(MatcherImpl.class);

  private List<Movie> movies;

  public MatcherImpl(CsvStream movieDb, CsvStream actorAndDirectorDb) {
    LOGGER.info("importing database");
    try {
      this.movies = CSVReaderUtil.readMovies(movieDb);
      CSVReaderUtil.addActorsAndDirectors(this.movies, actorAndDirectorDb);
    } catch (Exception e) {
      LOGGER.error("Error importing database", e);
    }
    LOGGER.info("database imported");
  }

  @Override
  public List<IdMapping> match(DatabaseType databaseType, CsvStream externalDb) {
    List<IdMapping> idMappings = new ArrayList<>();
    try {
      List<ProviderMovie> providerMovies = CSVReaderUtil.readProviderMovies(externalDb);

      for (ProviderMovie providerMovie : providerMovies) {
        Movie bestMatch = null;
        int highestScore = 0;

        for (Movie movie : movies) {
          int score = calculateMatchScore(movie, providerMovie);
          if (score > highestScore) {
            highestScore = score;
            bestMatch = movie;
          }
        }

        if (bestMatch != null) {
          idMappings.add(new IdMapping(bestMatch.getId(), providerMovie.getMediaId()));
          LOGGER.info("Matched {} with {}", providerMovie.getTitle(), bestMatch.getTitle());
        } else {
          LOGGER.info("No match found for {}", providerMovie.getTitle());
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error matching databases", e);
    }
    return idMappings;
  }

  private int calculateMatchScore(Movie movie, ProviderMovie providerMovie) {
    int score = 0;

    if (movie.getTitle().equalsIgnoreCase(providerMovie.getTitle())) {
      score += 50;
    }

    if (movie.getYear() == Integer.parseInt(providerMovie.getOriginalReleaseDate().split("/")[2])) {
      score += 30;
    }

    List<String> movieActors = movie.getActors();
    List<String> providerActors = providerMovie.getActors();
    for (String actor : providerActors) {
      if (movieActors.contains(actor)) {
        score += 5;
      }
    }

    if (movie.getDirector().equalsIgnoreCase(providerMovie.getDirector())) {
      score += 15;
    }

    return score;
  }
}
