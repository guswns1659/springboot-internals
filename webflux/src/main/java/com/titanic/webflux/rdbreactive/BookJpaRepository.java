package com.titanic.webflux.rdbreactive;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository extends CrudRepository<Book, Integer> {

    List<Book> findByIdBetween(int lower, int upper);

    @Query("select b from Book b where " +
            "LENGTH(b.title)=(SELECT MIN(LENGTH(b2.title)) FROM Book b2)")
    List<Book> findShortestTitle();
}
