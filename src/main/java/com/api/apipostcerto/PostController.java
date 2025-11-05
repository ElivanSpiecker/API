package com.api.apipostcerto;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // a. POST /post - criar um post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dtos.PostResponse create(@Validated @RequestBody Dtos.PostRequest req) {
        return toResponse(service.create(req));
    }

    // b. GET /post/count - consultar quantidade de posts
    @GetMapping("/count")
    public long count() {
        return service.count();
    }

    // c. GET /post - consulta todos posts (ordenado por dataHora desc)
    @GetMapping
    public List<Dtos.PostResponse> listAll() {
        return service.listAll().stream()
                .sorted(Comparator.comparing(Post::getDataHora).reversed())
                .map(PostController::toResponse)
                .collect(Collectors.toList());
    }

    // d. GET /post/{id} - consulta 1 post (id numérico)
    @GetMapping("/{id:\\d+}")
    public Dtos.PostResponse get(@PathVariable Long id) {
        return toResponse(service.get(id));
    }

    // e. GET /post/{exp} - consulta posts por expressão nos comentários (quando não for número)
    @GetMapping("/{exp}")
    public List<Dtos.PostResponse> search(@PathVariable String exp) {
        return service.searchByComentario(exp).stream()
                .sorted(Comparator.comparing(Post::getDataHora).reversed())
                .map(PostController::toResponse)
                .collect(Collectors.toList());
    }

    // Extras (não solicitados, mas úteis para CRUD completo)
    @PutMapping("/{id:\\d+}")
    public Dtos.PostResponse update(@PathVariable Long id, @Validated @RequestBody Dtos.PostRequest req) {
        return toResponse(service.update(id, req));
    }

    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id:\\d+}/like")
    public Dtos.PostResponse like(@PathVariable Long id) {
        return toResponse(service.like(id));
    }

    private static Dtos.PostResponse toResponse(Post p) {
        Dtos.PostResponse r = new Dtos.PostResponse();
        r.id = p.getId();
        r.quem = p.getQuem();
        r.dataHora = p.getDataHora();
        r.comentario = p.getComentario();
        r.likes = p.getLikes();
        return r;
    }
}
