package com.titanic.webmvc.jdbc;

import java.util.Set;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.MappedCollection;

@Getter
public class Dish {
    @Id
    private Long id;
    private String name;
    @MappedCollection(idColumn = "dish_id")
    private Set<Image> images;
}
