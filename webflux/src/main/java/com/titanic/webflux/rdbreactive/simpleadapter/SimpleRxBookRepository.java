package com.titanic.webflux.rdbreactive.simpleadapter;

import com.titanic.webflux.rdbreactive.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRxBookRepository extends JpaRepository<Book, Integer> {
}
