package com.bs.controller;

import com.bs.entity.Music;
import com.bs.entity.User;
import com.bs.exceptions.BusinessException;
import com.bs.repository.MusicRepository;
import com.bs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    //   登陆
    @GetMapping("")
    public ResponseEntity getOneUser(@RequestParam("username") String username,@RequestParam("password") String password) throws BusinessException {
        User user = userRepository.findByUsernameAndPassword(username,password);
        if(user == null){
            throw new BusinessException("请输入正确的用户名或密码");
        }
        return new ResponseEntity<>("登陆成功", HttpStatus.OK);
    }


    //    注册
    @PostMapping("")
    public ResponseEntity addUser(@RequestBody User user) throws BusinessException {
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 != null){
            throw new BusinessException("该用户名已经被注册");
        }
        else {
            userRepository.save(user);
            return new ResponseEntity<>("1",HttpStatus.CREATED);
        }
    }


    //    注销
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        userRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
