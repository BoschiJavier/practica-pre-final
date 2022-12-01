package com.spotify.sugeridos.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(name = "api-music")
public interface MusicFeign {


    @GetMapping("/api/v1/musics")
    List<Music> getAll();

    @Getter
    @Setter
    class Music {
        private Long musicId;
        private String name;
        private Integer mgCount = 0;
    }
}
