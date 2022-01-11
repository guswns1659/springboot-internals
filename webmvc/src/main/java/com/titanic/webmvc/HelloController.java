package com.titanic.webmvc;

import com.titanic.webmvc.shard.service.UserTxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final UserTxService userTxService;

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user/{userId}/{type}")
    public String user(@PathVariable("userId") Long userId, @PathVariable("type") String type) {
        return userTxService.save(userId, type);
    }
}
