package com.spotify.sugeridos.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "api-playlist")
public interface PlaylistFeign {

    @GetMapping("/api/v1/playlists")
    List<PlaylistFeign.Playlist> getAll();

    @Getter
    @Setter
    class Playlist {
        private Long playListId;
        private String name;
        private Integer mgCount;
        private List<PlaylistFeign.Playlist.Music> musics;

        @Getter
        @Setter
        static class Music {
            private Long musicId;
            private String musicName;
        }
    }
}
