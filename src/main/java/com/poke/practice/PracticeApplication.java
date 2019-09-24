package com.poke.practice;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class PracticeApplication {

  @Entity
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Table(name = "USER_ENTITY")
  public static class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String gender;
  }

  @RestController
  @RequestMapping("users")
  public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("{id}")
    public UserEntity get(@PathVariable Long id) throws Exception {
      return userRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));
    }

    @GetMapping("{id}/name")
    public List<String> findNameById(@PathVariable Long id) throws Exception {
      return userRepository.findNameById(id);
    }

    @GetMapping("{id}/nameAndGender")
    public UserDetail findNameAndGenderById(@PathVariable Long id) throws Exception {
      return userRepository.findNameAndGenderById(id);
    }

    @PostMapping
    public UserEntity post(@RequestBody UserEntity userEntity) {
      return userRepository.save(userEntity);
    }

    @PutMapping("{id}")
    public UserEntity put(@PathVariable Long id, @RequestBody UserEntity userRequest) throws Exception {
      return userRepository.findById(id).map(userFromDb -> {
        userFromDb.setName(userRequest.getName());
        userFromDb.setGender(userRequest.getGender());
        return userRepository.save(userFromDb);
      }).orElseThrow(() -> new Exception("Not Found"));
    }

  }

  public static void main(String[] args) {
    SpringApplication.run(PracticeApplication.class, args);
  }

}
