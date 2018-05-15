package com.bs.controller;

import com.bs.entity.User;
import com.bs.exceptions.BusinessException;
import com.bs.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.bs.utils.Md5Util.getMD5;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //   登陆
    @GetMapping("")
    public ResponseEntity getOneUser(@RequestParam("username") String username,@RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException {
        User user = userRepository.findByUsernameAndPassword(username,getMD5(getMD5(password)));
            if(user == null){
            throw new BusinessException("请输入正确的用户名或密码");
        }
        String token = generateToken(user.getId());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private String generateToken(Long userId) throws UnsupportedEncodingException {
        String SECRET = "music";
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        String jwt = Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes("UTF-8"))
                .compact();
        return jwt;
    }
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

    @GetMapping("/username")
    public String loadUserByUsername(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("music")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims.getSubject();
    }
}
