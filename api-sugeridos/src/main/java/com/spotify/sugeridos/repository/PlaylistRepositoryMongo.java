package com.spotify.sugeridos.repository;

import com.spotify.sugeridos.model.PlaylistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepositoryMongo extends MongoRepository<PlaylistEntity,Long> {
}
