package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/anime")
public class AnimeServlet extends HttpServlet {
    private AnimeService animeService;

    @Override
    public void init() throws ServletException {
        animeService = new AnimeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Anime> animeList = animeService.getAllAnime();
        String json = new Gson().toJson(animeList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        // Десериализация JSON в объект Anime
        Anime newAnime = new Gson().fromJson(json, Anime.class);

        // Запись объекта Anime в базу данных MySQL
        animeService.addAnime(newAnime);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
