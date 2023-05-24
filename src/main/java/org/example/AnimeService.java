package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimeService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/anime_library";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public List<Anime> getAllAnime() {
        List<Anime> animeList = new ArrayList<>();

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

    public void addAnime(Anime anime) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO anime (title, format, release_date, genre, status) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, anime.getTitle());
            stmt.setString(2, anime.getFormat());
            stmt.setString(3, anime.getReleaseDate());
            stmt.setString(4, anime.getGenre());
            stmt.setString(5, anime.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

