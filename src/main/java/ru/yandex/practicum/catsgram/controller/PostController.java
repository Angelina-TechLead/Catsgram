package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> findAll(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        if (size <= 0) {
            throw new IllegalArgumentException("Параметр size должен быть больше 0");
        }
        SortOrder sortOrder = SortOrder.from(sort);
        if (sortOrder == null) {
            throw new IllegalArgumentException("Некорректное значение параметра sort: " + sort);
        }
        return postService.findAll(from, size, sortOrder);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

    public enum SortOrder {
        ASCENDING, DESCENDING;

        public static SortOrder from(String order) {
            switch (order.toLowerCase()) {
                case "asc":
                case "ascending":
                    return ASCENDING;
                case "desc":
                case "descending":
                    return DESCENDING;
                default:
                    return null;
            }
        }
    }
}
