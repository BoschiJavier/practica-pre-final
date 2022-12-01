package com.spotify.music.event;

import com.spotify.music.config.RabbitMQConfig;
import com.spotify.music.model.Music;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NewMusicEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public NewMusicEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void execute(Music musicNew) {
        NewMusicEventProducer.Data data= new NewMusicEventProducer.Data();
        BeanUtils.copyProperties(data.getMusic(),musicNew);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_MUSIC, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data{
        private MusicDto music;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MusicDto{
            private Long musicId;
            private String name;
            private String singerName;
        }

    }
}
