package com.spotify.sugeridos.service;


import com.spotify.sugeridos.client.MusicFeign;
import com.spotify.sugeridos.client.PlaylistFeign;
import com.spotify.sugeridos.controller.GetSugerenciaByPopularidadResponse;
import com.spotify.sugeridos.repository.MusicRepositoryMongo;
import com.spotify.sugeridos.repository.PlaylistRepositoryMongo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SugeridoService {

    private final Map<String, Integer> popularidadMg;

    private final MusicRepositoryMongo musicRepositoryMongo;
    private final PlaylistRepositoryMongo playlistRepositoryMongo;

    private final MusicFeign musicFeign;
    private final PlaylistFeign playlistFeign;

    public SugeridoService(MusicRepositoryMongo musicRepositoryMongo, PlaylistRepositoryMongo playlistRepositoryMongo, MusicFeign musicFeign, PlaylistFeign playlistFeign) {
        this.musicRepositoryMongo = musicRepositoryMongo;
        this.playlistRepositoryMongo = playlistRepositoryMongo;
        this.musicFeign = musicFeign;
        this.playlistFeign = playlistFeign;
        this.popularidadMg = new HashMap<>();
        popularidadMg.put("MUY_POPULAR", 3);
        popularidadMg.put("NORMAL", 1);
    }

    public GetSugerenciaByPopularidadResponse getSugerenciaByPopularidadOnline(String popularidad) {
        GetSugerenciaByPopularidadResponse response = new GetSugerenciaByPopularidadResponse();
        findAllMusicByPopularidad(popularidad, response);
        findAllPlaylistByPopularidad(popularidad, response);
        return response;
    }

    @Retry(name = "retryMusic")
    @CircuitBreaker(name = "clientMusic", fallbackMethod = "findAllPlaylistFallBack")
    private void findAllPlaylistByPopularidad(String popularidad, GetSugerenciaByPopularidadResponse response) {
        var playListFilter = playlistFeign.getAll().stream().filter(playlist -> playlist.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setPlaylist(playListFilter.stream().map(playlist -> {
            GetSugerenciaByPopularidadResponse.PlaylistDto playlistResponse = new GetSugerenciaByPopularidadResponse.PlaylistDto();
            BeanUtils.copyProperties(playlist, playlistResponse);
            return playlistResponse;
        }).collect(Collectors.toList()));
    }

   public void findAllPlaylistFallBack(String popularidad, GetSugerenciaByPopularidadResponse response, Throwable t) {
       var playListFilter = playlistRepositoryMongo.findAll().stream().filter(playlist -> playlist.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
       response.setPlaylist(playListFilter.stream().map(playlist -> {
           GetSugerenciaByPopularidadResponse.PlaylistDto playlistResponse = new GetSugerenciaByPopularidadResponse.PlaylistDto();
           BeanUtils.copyProperties(playlist, playlistResponse);
           return playlistResponse;
       }).collect(Collectors.toList()));
   }

    @Retry(name = "retryMusic")
    @CircuitBreaker(name = "clientMusic", fallbackMethod = "findAllMusicFallBack")
    private void findAllMusicByPopularidad(String popularidad, GetSugerenciaByPopularidadResponse response) {
        var musicsFilter = musicFeign.getAll().stream().filter(music -> music.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMusics(musicsFilter.stream().map(music -> {
            GetSugerenciaByPopularidadResponse.MusicDto musicResponse = new GetSugerenciaByPopularidadResponse.MusicDto();
            BeanUtils.copyProperties(music, musicResponse);
            return musicResponse;
        }).collect(Collectors.toList()));
    }

    private void findAllMusicFallBack(String popularidad, GetSugerenciaByPopularidadResponse response, Throwable t) {
        var musicsFilter = musicRepositoryMongo.findAll().stream().filter(music -> music.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMusics(musicsFilter.stream().map(music -> {
            GetSugerenciaByPopularidadResponse.MusicDto musicResponse = new GetSugerenciaByPopularidadResponse.MusicDto();
            BeanUtils.copyProperties(music, musicResponse);
            return musicResponse;
        }).collect(Collectors.toList()));
    }

    public GetSugerenciaByPopularidadResponse getSugerenciaByPopularidadOffline(String popularidad) {
        GetSugerenciaByPopularidadResponse response = new GetSugerenciaByPopularidadResponse();
        var musicsFilter = musicRepositoryMongo.findAll().stream().filter(music -> music.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMusics(musicsFilter.stream().map(music -> {
            GetSugerenciaByPopularidadResponse.MusicDto musicResponse = new GetSugerenciaByPopularidadResponse.MusicDto();
            BeanUtils.copyProperties(music, musicResponse);
            return musicResponse;
        }).collect(Collectors.toList()));

        var playListFilter = playlistRepositoryMongo.findAll().stream().filter(playlist -> playlist.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setPlaylist(playListFilter.stream().map(playlist -> {
            GetSugerenciaByPopularidadResponse.PlaylistDto playlistResponse = new GetSugerenciaByPopularidadResponse.PlaylistDto();
            BeanUtils.copyProperties(playlist, playlistResponse);
            return playlistResponse;
        }).collect(Collectors.toList()));
        return response;
    }
}
