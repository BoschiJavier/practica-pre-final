package com.spotify.sugeridos.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSugerenciaByPopularidadRequest {

    private List<GetSugerenciaByPopularidadRequest.MusicDto> musics = new ArrayList<>();
    private List<GetSugerenciaByPopularidadRequest.PlaylistDto> playlist= new ArrayList<>();
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicDto{
        private String name;
        private String singerName;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistDto{
        private String name;
    }

}
