package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimeService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/anime_library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Zeinel0338";

    public List<Anime> getAllAnime() {
        List<Anime> animeList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM anime")) {

            while (rs.next()) {
                Anime anime = new Anime();
                anime.setId(rs.getInt("id"));
                anime.setTitle(rs.getString("title"));
                anime.setFormat(rs.getString("format"));
                anime.setReleaseDate(rs.getString("release_date"));
                anime.setGenre(rs.getString("genre"));
                anime.setStatus(rs.getString("status"));
                animeList.add(anime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animeList;
    }
    public Anime getAnimeById(int animeId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM anime WHERE id = ?")) {

            stmt.setInt(1, animeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Anime anime = new Anime();
                anime.setId(rs.getInt("id"));
                anime.setTitle(rs.getString("title"));
                anime.setFormat(rs.getString("format"));
                anime.setReleaseDate(rs.getString("release_date"));
                anime.setGenre(rs.getString("genre"));
                anime.setStatus(rs.getString("status"));
                return anime;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Если аниме с указанным идентификатором не найдено
    }

    public void addAnime(Anime anime) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO anime (title, format, release_date, genre, status) VALUES (?, ?, ?, ?, ?)")) {

                stmt.setString(1, anime.getTitle());
                stmt.setString(2, anime.getFormat());
                stmt.setString(3, anime.getReleaseDate());
                stmt.setString(4, anime.getGenre());
                stmt.setString(5, anime.getStatus());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAnime(Anime anime) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE anime SET title=?, format=?, release_date=?, genre=?, status=? WHERE id=?")) {

            stmt.setString(1, anime.getTitle());
            stmt.setString(2, anime.getFormat());
            stmt.setString(3, anime.getReleaseDate());
            stmt.setString(4, anime.getGenre());
            stmt.setString(5, anime.getStatus());
            stmt.setInt(6, anime.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAnime(int animeId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM anime WHERE id=?")) {

            stmt.setInt(1, animeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

