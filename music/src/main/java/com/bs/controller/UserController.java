package com.bs.controller;

import com.bs.entity.User;
import com.bs.exceptions.BusinessException;
import com.bs.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.bs.utils.Md5Util.getMD5;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //   登陆
    @GetMapping("")
    public ResponseEntity getOneUser(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) throws BusinessException {
        User user = userRepository.findByUsernameAndPassword(username,getMD5(getMD5(password)));
            if(user == null){
            throw new BusinessException("请输入正确的用户名或密码");
        }
        return new ResponseEntity<>("1", HttpStatus.OK);
    }

//    @GetMapping("/username")
//    public Authentication loadUserByUsername() {
//        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
//        return auth1;
//    }



    //    注册
    @PostMapping("")
    public ResponseEntity addUser(@RequestBody User user) throws BusinessException {
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 != null){
            throw new BusinessException("该用户名已经被注册");
        }
        else {
            String md5Password = getMD5(getMD5(user.getPassword()));
            user.setPassword(md5Password);
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
