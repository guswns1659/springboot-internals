package com.titanic.webflux.rdbreactive.simpleadapter;

import com.titanic.webflux.rdbreactive.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  Different with Hands-on spring 5
 *  I make reactive repository more simple through not using extends.
 */
@RequiredArgsConstructor
@RequestMapping("/simple")
@RestController
@Slf4j
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

    @GetMapping("/books/delete")
    public Mono<Object> delete() {
        return simpleRxBookRepositoryAdapter.deleteAll();
    }

    @GetMapping("/books/delete/batch")
    public Mono<Object> deleteBatch() {
        log.info(">>>>>>> deleteBatch");
        return simpleRxBookRepositoryAdapter.deleteAllInBatch();
    }

    @GetMapping("/books/delete/custom")
    public Mono<Object> deleteCustom() {
        log.info(">>>>>>> deleteCustom");
        return simpleRxBookRepositoryAdapter.deleteAllByIds(List.of(1, 2, 3));
    }
}
