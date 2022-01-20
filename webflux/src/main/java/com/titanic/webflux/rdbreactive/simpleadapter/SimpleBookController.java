package com.titanic.webflux.rdbreactive.simpleadapter;

import com.titanic.webflux.rdbreactive.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *  Different with Hands-on spring 5
 *  I make reactive repository more simple through not using extends.
 */
@RequiredArgsConstructor
@RequestMapping("/simple")
@RestController
public class SimpleBookController {

    private final SimpleRxBookRepositoryAdapter simpleRxBookRepositoryAdapter;

    @GetMapping(value = "/books/save")
    public Mono<Book> save() {
        return simpleRxBookRepositoryAdapter.save(new Book("title1", 2020));
    }

    @GetMapping("/{id}")
    public Mono<Book> get(@PathVariable(value = "id") Integer id) {
        return simpleRxBookRepositoryAdapter.read(id);
    }
}
