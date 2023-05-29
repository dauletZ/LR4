package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Anime {
    private static int nextId = 1;

    private int id;
    private String title;
    private String format;
    private String releaseDate;
    private String genre;
    private String status;

    public Anime(String title, String format, String releaseDate, String genre, String status) {
        this.id = nextId++;
        this.title = title;
        this.format = format;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.status = status;
    }

    public Anime() {
        
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("format", format);
        json.put("releaseDate", releaseDate);
        json.put("genre", genre);
        json.put("status", status);
        return json.toString();
    }

    public static String toJSONArray(List<Anime> animeList) {
        JSONArray jsonArray = new JSONArray();
        for (Anime anime : animeList) {
            jsonArray.put(new JSONObject(anime.toJSON()));
        }
        return jsonArray.toString();
    }

    public void setId(int id) {
        this.id = id;
    }
}