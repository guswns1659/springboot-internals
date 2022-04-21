package com.titanic.webmvc.jdbc;

import javax.persistence.Id;
import lombok.Getter;

@Getter
public class Image {
    @Id
    private Long id;
    private String name;
    private Long dishId;
}
