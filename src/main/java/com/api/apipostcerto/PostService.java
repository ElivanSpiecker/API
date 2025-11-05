package com.api.apipostcerto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public long count() {
        return repo.count();
    }

    public List<Post> listAll() {
        return repo.findAll();
    }

    public Page<Post> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Post get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Post não encontrado: " + id));
    }

    public List<Post> searchByComentario(String exp) {
        return repo.findByComentarioContainingIgnoreCase(exp);
    }

    public Post create(Dtos.PostRequest req) {
        Post p = new Post();
        p.setQuem(req.quem.trim());
        p.setComentario(req.comentario.trim());
        p.setDataHora(req.dataHora != null ? req.dataHora : Instant.now());
        p.setLikes(req.likes != null ? req.likes : 0);
        return repo.save(p);
    }

    public Post update(Long id, Dtos.PostRequest req) {
        Post p = get(id);
        p.setQuem(req.quem.trim());
        p.setComentario(req.comentario.trim());
        p.setDataHora(req.dataHora != null ? req.dataHora : p.getDataHora());
        p.setLikes(req.likes != null ? req.likes : p.getLikes());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }

    public Post like(Long id) {
        Post p = get(id);
        p.setLikes(p.getLikes() + 1);
        return repo.save(p);
    }
}
