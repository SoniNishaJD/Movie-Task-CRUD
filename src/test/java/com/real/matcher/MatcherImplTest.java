package com.real.matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MatcherImplTest {

  private MatcherImpl matcher;

  @BeforeEach
  public void setUp() {
    String movieDbCsv = "id,title,year\n" +
            "1,Finding Nemo,2003\n" +
            "2,Furious 7,2015\n" +
            "3,Inception,2010\n";

    String actorsAndDirectorsCsv = "movie_id,name,role\n" +
            "1,Albert Brooks,cast\n" +
            "1,Ellen DeGeneres,cast\n" +
            "1,Andrew Stanton,director\n" +
            "2,Vin Diesel,cast\n" +
            "2,Paul Walker,cast\n" +
            "2,James Wan,director\n" +
            "3,Leonardo DiCaprio,cast\n" +
            "3,Joseph Gordon-Levitt,cast\n" +
            "3,Christopher Nolan,director\n";

    CsvStream movieDbStream = new CsvStream(new ByteArrayInputStream(movieDbCsv.getBytes(StandardCharsets.UTF_8)));
    CsvStream actorsAndDirectorsStream = new CsvStream(new ByteArrayInputStream(actorsAndDirectorsCsv.getBytes(StandardCharsets.UTF_8)));

    matcher = new MatcherImpl(movieDbStream, actorsAndDirectorsStream);
  }

  @Test
  public void testMatch() {
    String providerDbCsv = "MediaId,Title,OriginalReleaseDate,MediaType,Actors,Director,XboxLiveURL\n" +
            "531b964f-0cb9-4968-9b77-e547f2435225,Furious 7,4/13/2015,Movie,Vin Diesel, Paul Walker, Jason Statham,James Wan,video.xbox.com\n" +
            "df8d1e1e-97d9-4a55-9c90-27f87199e7c1,Finding Nemo,5/30/2003,Movie,Albert Brooks, Ellen DeGeneres, Andrew Stanton,video.xbox.com\n";

    CsvStream providerDbStream = new CsvStream(new ByteArrayInputStream(providerDbCsv.getBytes(StandardCharsets.UTF_8)));

    List<IdMapping> mappings = matcher.match(DatabaseType.EXTERNAL, providerDbStream);

    assertNotNull(mappings);
    assertEquals(2, mappings.size());

    assertEquals(2, mappings.get(0).getMovieId());
    assertEquals("531b964f-0cb9-4968-9b77-e547f2435225", mappings.get(0).getProviderMediaId());

    assertEquals(1, mappings.get(1).getMovieId());
    assertEquals("df8d1e1e-97d9-4a55-9c90-27f87199e7c1", mappings.get(1).getProviderMediaId());
  }

  // Add more tests to cover various edge cases and scenarios

  @Test
  public void testNoMatch() {
    String providerDbCsv = "MediaId,Title,OriginalReleaseDate,MediaType,Actors,Director,XboxLiveURL\n" +
            "1111-1111-1111-1111-111111111111,Unknown Movie,1/1/2022,Movie,Unknown Actor,Unknown Director,video.xbox.com\n";

    CsvStream providerDbStream = new CsvStream(new ByteArrayInputStream(providerDbCsv.getBytes(StandardCharsets.UTF_8)));

    List<IdMapping> mappings = matcher.match(DatabaseType.EXTERNAL, providerDbStream);

    assertNotNull(mappings);
    assertEquals(0, mappings.size());
  }
}
