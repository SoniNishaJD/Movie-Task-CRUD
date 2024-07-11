package com.real.matcher;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private int year;
    private List<String> actors = new ArrayList<>();
    private String director;

    public Movie(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public List<String> getActors() {
        return actors;
    }

    public String getDirector() {
        return director;
    }

    public void addActor(String actor) {
        this.actors.add(actor);
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", actors=" + actors +
                ", director='" + director + '\'' +
                '}';
    }
}
