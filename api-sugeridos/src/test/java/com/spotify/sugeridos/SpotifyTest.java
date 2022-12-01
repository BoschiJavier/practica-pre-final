package com.spotify.sugeridos;

import com.spotify.sugeridos.controller.GetSugerenciaByPopularidadResponse;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotifyTest extends BaseAPI {

    @Test
    @Tag("functional")
    @DisplayName("Testear toda la aplicacion con api gateway")
    public void testingAll() {

        given().
                contentType(ContentType.JSON).
                body(
                        new MusicRequest(158L, "Nombre", "Cantante", 1000)
                ).
                when().post("/api/v1/musics");

        given().
                contentType(ContentType.JSON).
                body(
                        new PlayListRequest(365L, "playlistNueva", 1000)
                ).
                when().post("/api/v1/playlists");

        GetSugerenciaByPopularidadResponse responseOnline = given()
                .param("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/sugeridos/online/{popularidad}")
                .as(GetSugerenciaByPopularidadResponse.class);

        GetSugerenciaByPopularidadResponse responseOffline = given()
                .param("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/sugeridos/offline/{popularidad}")
                .as(GetSugerenciaByPopularidadResponse.class);

        assertEquals(responseOnline.getMusics().size(), 1);
        assertEquals(responseOnline.getPlaylist().size(), 1);

        assertEquals(responseOffline.getMusics().size(), 1);
        assertEquals(responseOffline.getPlaylist().size(), 1);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicRequest {
        private Long musicId;
        private String name;
        private String singerName;
        private Integer mgCount = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayListRequest {
        private Long playListId;
        private String name;
        private Integer mgCount = 0;
    }
}
