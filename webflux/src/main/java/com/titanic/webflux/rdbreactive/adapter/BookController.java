package com.titanic.webflux.rdbreactive.adapter;

import com.titanic.webflux.rdbreactive.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final RxBookRepository rxBookRepository;

    @GetMapping("/books/insert")
    public String insert() {
        List<Book> books = List.of(
                new Book("title1", 2019),
                new Book("title2", 2020),
                new Book("title3", 2021));

        rxBookRepository.saveAll(books)
                .doOnNext(book -> log.info("book - {}", book.toString()))
                .subscribe();

        return "success";
    }
}
