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

@WebServlet("/anime/*")
public class AnimeServlet extends HttpServlet {
    private AnimeService animeService;
    @Override
    public void init() throws ServletException {
        animeService = new AnimeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Anime> animeList = animeService.getAllAnime();
            String json = new Gson().toJson(animeList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            int animeId = Integer.parseInt(request.getPathInfo().substring(1));
            Anime anime = animeService.getAnimeById(animeId);
            if (anime != null) {
                String json = new Gson().toJson(anime);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String format = request.getParameter("format");
        String releaseDate = request.getParameter("releaseDate");
        String genre = request.getParameter("genre");
        String status = request.getParameter("status");

        Anime anime = new Anime();
        anime.setTitle(title);
        anime.setFormat(format);
        anime.setReleaseDate(releaseDate);
        anime.setGenre(genre);
        anime.setStatus(status);

        animeService.addAnime(anime);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int animeId = Integer.parseInt(request.getPathInfo().substring(1));
        Anime anime = animeService.getAnimeById(animeId);
        if (anime != null) {
            String title = request.getParameter("title");
            String format = request.getParameter("format");
            String releaseDate = request.getParameter("releaseDate");
            String genre = request.getParameter("genre");
            String status = request.getParameter("status");

            anime.setTitle(title);
            anime.setFormat(format);
            anime.setReleaseDate(releaseDate);
            anime.setGenre(genre);
            anime.setStatus(status);

            animeService.updateAnime(anime);

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int animeId = Integer.parseInt(request.getPathInfo().substring(1));
        Anime anime = animeService.getAnimeById(animeId);
        if (anime != null) {
            animeService.deleteAnime(animeId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

