package com.titanic.messaging.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lock")
@Slf4j
@JackLock // Explain this Controller use Lock even though this annotation doesn't work
public class LockController {

    @Autowired
    private LockService lockService;

    @GetMapping("/redis")
    public String lock() throws InterruptedException {
        lockService.pointCut("args");
        Thread.sleep(3000);
        return "success";
    }
}

@Service
@Slf4j
class LockService {
    @JackLock
    public String pointCut(String args) {
        log.info(">>>>>>>>>>>>> pointCut args - {}", args);
        return "pointCut";
    }
}
