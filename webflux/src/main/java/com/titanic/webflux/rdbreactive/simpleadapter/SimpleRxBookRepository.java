package com.titanic.webflux.rdbreactive.simpleadapter;

import com.titanic.webflux.rdbreactive.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SimpleRxBookRepository extends JpaRepository<Book, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from Book b where b.id in :ids")
    void deleteAllByIds(@Param("ids") List<Integer> ids);

}
