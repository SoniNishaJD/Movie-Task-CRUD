package com.real.matcher;

public class IdMapping {
    private int movieId;
    private String providerMediaId;

    public IdMapping(int movieId, String providerMediaId) {
        this.movieId = movieId;
        this.providerMediaId = providerMediaId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getProviderMediaId() {
        return providerMediaId;
    }

    @Override
    public String toString() {
        return "IdMapping{" +
                "movieId=" + movieId +
                ", providerMediaId='" + providerMediaId + '\'' +
                '}';
    }
}
