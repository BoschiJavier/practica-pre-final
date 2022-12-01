package com.spotify.sugeridos.service;


import com.spotify.sugeridos.client.MusicFeign;
import com.spotify.sugeridos.client.PlaylistFeign;
import com.spotify.sugeridos.controller.GetSugerenciaByPopularidadRequest;
import com.spotify.sugeridos.repository.MusicRepositoryMongo;
import com.spotify.sugeridos.repository.PlaylistRepositoryMongo;
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
        popularidadMg.put("MUY_POPULAR", 10);
        popularidadMg.put("NORMAL", 3);
    }

    public GetSugerenciaByPopularidadRequest getSugerenciaByPopularidadOnline(String popularidad) {
        GetSugerenciaByPopularidadRequest response = new GetSugerenciaByPopularidadRequest();
        var musicsFilter = musicFeign.getAll().stream().filter(music -> music.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMusics(musicsFilter.stream().map(music -> {
            GetSugerenciaByPopularidadRequest.MusicDto musicResponse = new GetSugerenciaByPopularidadRequest.MusicDto();
            BeanUtils.copyProperties(music, musicResponse);
            return musicResponse;
        }).collect(Collectors.toList()));

        var playListFilter = playlistFeign.getAll().stream().filter(playlist -> playlist.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setPlaylist(playListFilter.stream().map(playlist -> {
            GetSugerenciaByPopularidadRequest.PlaylistDto playlistResponse = new GetSugerenciaByPopularidadRequest.PlaylistDto();
            BeanUtils.copyProperties(playlist, playlistResponse);
            return playlistResponse;
        }).collect(Collectors.toList()));
        return response;
    }

    public GetSugerenciaByPopularidadRequest getSugerenciaByPopularidadOffline(String popularidad) {
        GetSugerenciaByPopularidadRequest response = new GetSugerenciaByPopularidadRequest();
        var musicsFilter = musicRepositoryMongo.findAll().stream().filter(music -> music.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMusics(musicsFilter.stream().map(music -> {
            GetSugerenciaByPopularidadRequest.MusicDto musicResponse = new GetSugerenciaByPopularidadRequest.MusicDto();
            BeanUtils.copyProperties(music, musicResponse);
            return musicResponse;
        }).collect(Collectors.toList()));

        var playListFilter = playlistRepositoryMongo.findAll().stream().filter(playlist -> playlist.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setPlaylist(playListFilter.stream().map(playlist -> {
            GetSugerenciaByPopularidadRequest.PlaylistDto playlistResponse = new GetSugerenciaByPopularidadRequest.PlaylistDto();
            BeanUtils.copyProperties(playlist, playlistResponse);
            return playlistResponse;
        }).collect(Collectors.toList()));
        return response;
    }
}
