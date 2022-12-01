package com.spotify.sugeridos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Playlist")
public class PlaylistEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long playListId;
    private String name;
    private Integer mgCount = 0;
    private List<PlayListMusic> musics = new ArrayList<>();


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayListMusic {


        private Long musicId;
        private String musicName;
    }

}
