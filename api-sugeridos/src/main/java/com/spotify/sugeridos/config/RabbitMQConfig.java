package com.spotify.sugeridos.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "spotifyExchange";
    public static final String TOPIC_NEW_MUSIC = "com.spotify.newMusic";
    public static final String TOPIC_NEW_PLAYLIST = "com.spotify.newPlaylist";
    public static final String QUEUE_NEW_MUSIC = "newMusicQueue";
    public static final String QUEUE_NEW_PLAYLIST =  "newPlaylistQueue";


    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }



    @Bean
    public Queue newMusicQueue() {
        return new Queue(QUEUE_NEW_MUSIC);
    }


    @Bean
    public Queue newPlaylistQueue() {
        return new Queue(QUEUE_NEW_PLAYLIST);
    }


    @Bean
    public Binding declareBindingSpecificNewMusic() {
        return BindingBuilder.bind(newMusicQueue()).to(appExchange()).with(TOPIC_NEW_MUSIC);
    }

    @Bean
    public Binding declareBindingSpecificNewPlaylist() {
        return BindingBuilder.bind(newPlaylistQueue()).to(appExchange()).with(QUEUE_NEW_PLAYLIST);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
