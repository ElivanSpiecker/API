package com.api.apipostcerto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public class Dtos {
    public static class PostRequest {
        @NotBlank(message = "quem é obrigatório")
        @Size(max = 255)
        public String quem;

        @NotBlank(message = "comentario é obrigatório")
        @Size(max = 4000)
        public String comentario;

        // opcional: permitir sobrescrever a data/hora
        public Instant dataHora;

        // opcional: definir likes inicialmente (>=0)
        @Min(0)
        public Integer likes;
    }

    public static class PostResponse {
        public Long id;
        public String quem;
        public Instant dataHora;
        public String comentario;
        public int likes;
    }
}
