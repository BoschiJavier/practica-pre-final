package com.spotify.music.event;

import com.spotify.music.config.RabbitMQConfig;
import com.spotify.music.model.Music;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@Slf4j
public class NewMusicEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public NewMusicEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void execute(Music musicNew) {
        NewMusicEventProducer.Data data= new NewMusicEventProducer.Data();
        BeanUtils.copyProperties(musicNew,data.getMusic());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_MUSIC, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private Data.MusicDto music= new Data.MusicDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MusicDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;
            private Long musicId;
            private String name;
            private String singerName;
            private Integer mgCount;
        }

    }
}
