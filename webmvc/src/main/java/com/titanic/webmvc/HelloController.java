package com.titanic.webmvc;

import com.titanic.webmvc.shard.service.UserTxService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/jackson")
    public ResponseDto jackson() {
        return new ResponseDto("name", "age");
    }

    @PostMapping("/jackson/request")
    public ResponseDto jacksonRequest(@RequestBody RequestDto requestDto) {
        return new ResponseDto("name", "age");
    }
}

class RequestDto {
    private String name;

    private RequestDto() {
    }

    public RequestDto(String name) {
        this.name = name;
    }
}

@Getter
class ResponseDto {
    private String name;
    private String age;

    public ResponseDto(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
