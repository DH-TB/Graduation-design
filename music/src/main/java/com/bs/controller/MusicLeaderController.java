package com.bs.controller;

import com.bs.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class MusicLeaderController {
    @Autowired
    private CollectionRepository collectionRepository;

}
