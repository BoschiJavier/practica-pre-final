package com.spotify.music.service;

import com.spotify.music.event.NewMusicEventProducer;
import com.spotify.music.model.Music;
import com.spotify.music.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    private final NewMusicEventProducer newMusicEventProducer;

    public MusicService(MusicRepository musicRepository, NewMusicEventProducer newMusicEventProducer) {
        this.musicRepository = musicRepository;
        this.newMusicEventProducer = newMusicEventProducer;
    }

    public void save(Music music) {
        musicRepository.save(music);
        newMusicEventProducer.execute(music);
    }


    public List<Music> getAll() {
        return musicRepository.findAll();
    }

    public Music getById(Long id) {
        return musicRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        musicRepository.deleteById(id);
    }

    public void update(Music music) {
        if(musicRepository.existsById(music.getMusicId())){
            musicRepository.save(music);
        }
    }
}
