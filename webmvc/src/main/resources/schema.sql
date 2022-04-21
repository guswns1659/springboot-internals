CREATE TABLE document
(
    id   INT         NOT NULL,
    name VARCHAR(50) NOT NULL
);

drop table if exists DISH;
CREATE TABLE DISH
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) not null
);

drop table if exists IMAGE;
CREATE TABLE IMAGE
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(50) not null,
    dish_id BIGINT,
    foreign key (dish_id) references DISH (id)
);
