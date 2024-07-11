package com.real.matcher;

import java.util.Arrays;
import java.util.List;

public class ProviderMovie {
    private String mediaId;
    private String title;
    private String originalReleaseDate;
    private String mediaType;
    private List<String> actors;
    private String director;
    private String xboxLiveURL;

    public ProviderMovie(String mediaId, String title, String originalReleaseDate, String mediaType, String actors, String director, String xboxLiveURL) {
        this.mediaId = mediaId;
        this.title = title;
        this.originalReleaseDate = originalReleaseDate;
        this.mediaType = mediaType;
        this.actors = Arrays.asList(actors.split(","));
        this.director = director;
        this.xboxLiveURL = xboxLiveURL;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public String getMediaType() {
        return mediaType;
    }

    public List<String> getActors() {
        return actors;
    }

    public String getDirector() {
        return director;
    }

    public String getXboxLiveURL() {
        return xboxLiveURL;
    }

    @Override
    public String toString() {
        return "ProviderMovie{" +
                "mediaId='" + mediaId + '\'' +
                ", title='" + title + '\'' +
                ", originalReleaseDate='" + originalReleaseDate + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", actors=" + actors +
                ", director='" + director + '\'' +
                ", xboxLiveURL='" + xboxLiveURL + '\'' +
                '}';
    }
}
