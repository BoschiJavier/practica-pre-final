package com.spotify.sugeridos.event;

import com.spotify.sugeridos.config.RabbitMQConfig;
import com.spotify.sugeridos.model.PlaylistEntity;
import com.spotify.sugeridos.repository.PlaylistRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewPlaylistEventConsumer {

    private final PlaylistRepositoryMongo playlistRepositoryMongo;

    public NewPlaylistEventConsumer(PlaylistRepositoryMongo playlistRepositoryMongo) {
        this.playlistRepositoryMongo = playlistRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_PLAYLIST)
    public void execute(NewPlaylistEventConsumer.Data data) {
        PlaylistEntity playlistNew= new PlaylistEntity();
        BeanUtils.copyProperties(data.getPlaylist(),playlistNew);
        BeanUtils.copyProperties(data.getPlaylist().getMusics(),playlistNew.getMusics());
        playlistRepositoryMongo.deleteById(data.getPlaylist().getPlayListId());
        playlistRepositoryMongo.save(playlistNew);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private Data.PlaylistDto playlist= new PlaylistDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PlaylistDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;
            private Long playListId;
            private String name;
            private Integer mgCount;
            private List<Data.PlaylistDto.PlaylistMusicDto> musics = new ArrayList<>();

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class PlaylistMusicDto implements Serializable {

                @Serial
                private static final long serialVersionUID = 1L;
                private Long musicId;
                private String musicName;
            }
        }

    }
}
