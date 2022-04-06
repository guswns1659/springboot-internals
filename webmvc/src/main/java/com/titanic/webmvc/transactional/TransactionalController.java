package com.titanic.webmvc.transactional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionalController {

    private final TransactionalService transactionalService;

    @GetMapping("/require_new")
    public String requireNew() throws InterruptedException, IllegalAccessException {
        transactionalService.requireNew();
        return "success";
    }

}

@Service
@RequiredArgsConstructor
class TransactionalService {

    private final TestEntityRepo testEntityRepo;
    private final AnotherService anotherService;

    public String requireNew() throws InterruptedException {
        testEntityRepo.save(new TestEntity("name2"));
        anotherService.throwMethod();

        return "success";
    }
}

@Service
@RequiredArgsConstructor
class AnotherService {
    private final TestEntityRepo testEntityRepo;

    @Transactional
    public void throwMethod() throws InterruptedException {
        testEntityRepo.save(new TestEntity("name-another"));

        Thread.sleep(2000);
        throw new RuntimeException();
    }
}

@Repository
interface TestEntityRepo extends JpaRepository<TestEntity, Long> {

}
