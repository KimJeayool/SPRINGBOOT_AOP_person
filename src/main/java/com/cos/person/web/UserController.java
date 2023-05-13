package com.cos.person.web;

import com.cos.person.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor // DI: 의존성 주입
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/user")
    public CommonDTO<List<User>> findAll() {
        log.info("findAll() 실행");

        // MessageConverter
        // Java Object => Json String
        return new CommonDTO<>(HttpStatus.OK.value(), userRepository.findAll()) ;
    }

    @GetMapping("/user/{id}")
    public CommonDTO<User> findById(@PathVariable int id) {
        log.info("findById() 실행 id={}", id);
        return new CommonDTO<>(HttpStatus.OK.value(), userRepository.findById(id)) ;
    }

    @CrossOrigin  // CORS 정책 사용 안 함
    @PostMapping("/user")
    public CommonDTO<String> save(@RequestBody JoinReqDTO dto) {
        // log.info("save() 실행 username={}, password={}, phone={}", username, password, phone);
        log.info("save() 실행 dto={}", dto.toString());
        userRepository.save(dto);

        return new CommonDTO<>(HttpStatus.CREATED.value(), "OK");
    }

    @DeleteMapping("/user/{id}")
    public CommonDTO<String> delete(@PathVariable int id) {
        log.info("delete() 실행 id={}", id);
        userRepository.delete(id);

        return new CommonDTO<>(HttpStatus.OK.value(), "OK");
    }

    @PutMapping("/user/{id}")
    public CommonDTO<String> update(@PathVariable int id, @RequestBody UpdateReqDTO dto) {
        log.info("update() 실행 id={}, dto={}", id, dto);
        userRepository.update(id, dto);

        return new CommonDTO<>(HttpStatus.OK.value(), "OK");
    }

}
