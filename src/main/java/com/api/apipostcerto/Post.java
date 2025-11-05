package com.api.apipostcerto;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String quem; // author

    @Column(nullable = false)
    private Instant dataHora; // timestamp (UTC)

    @Column(nullable = false, length = 4000)
    private String comentario;

    @Column(nullable = false)
    private int likes = 0;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuem() { return quem; }
    public void setQuem(String quem) { this.quem = quem; }

    public Instant getDataHora() { return dataHora; }
    public void setDataHora(Instant dataHora) { this.dataHora = dataHora; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
