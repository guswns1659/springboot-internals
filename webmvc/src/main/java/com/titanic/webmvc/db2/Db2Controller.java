package com.titanic.webmvc.db2;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class Db2Controller {

    private final CarRepository carRepository;

    @GetMapping("db2/{start-date}/{end-date}")
    public String db2(
        @PathVariable("start-date") Long startDate,
        @PathVariable("end-date") Long endDate
    ) {

        carRepository.save(Car.builder().date(new Timestamp(1420070401L * 1000)).build());

        Date start = new Date(TimeUnit.SECONDS.toMillis(startDate));
        Date end = new Date(TimeUnit.SECONDS.toMillis(endDate));
        List<Car> cars = carRepository.findAll(start, end);
        System.out.println(">>> " + cars);
        return "success";
    }
}

@Entity
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp date;
}

@Repository
interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.date between :startDate and :endDate")
    List<Car> findAll(Date startDate, Date endDate);
}
