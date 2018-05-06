package com.bs.controller;

import com.bs.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserStyleController {
    @Autowired
    private MusicRepository musicRepository;

    @PutMapping("/userStyle")
    public ResponseEntity setUserStyle(){

        return new ResponseEntity(HttpStatus.OK);
    }
}
