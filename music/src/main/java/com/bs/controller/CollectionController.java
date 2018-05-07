package com.bs.controller;

import com.bs.entity.*;
import com.bs.entity.Collection;
import com.bs.exceptions.BusinessException;
import com.bs.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(value = "我的歌单")
@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MusicLeaderRepository musicLeaderRepository;
    @Autowired
    private SignerLeaderRepository signerLeaderRepository;
    @Autowired
    private SignerRepository signerRepository;
    @Autowired
    private MusicStyleRepository musicStyleRepository;
    @Autowired
    private UserStyleRepository userStyleRepository;

    //   获取我的收藏列表
    @ApiOperation(value = "获取我的收藏列表")
    @GetMapping("/{userId}")
    public ResponseEntity getAllCollectionMusic(@PathVariable Long userId) {
        List<Collection> collections = collectionRepository.findByUserId(userId);
        List collectionList = new ArrayList<>();
        for (Collection collection : collections) {
            ObjectMapper oMapper = new ObjectMapper();
            Map data = oMapper.convertValue(collection, Map.class);
            Music music = musicRepository.findOne(collection.getMusicId());
            Singer singer = signerRepository.findOne(music.getSingerId());
            data.put("singer", singer.getSinger());
            data.put("music", music.getMusic());
            data.put("album", music.getAlbum());
            collectionList.add(data);
        }
        return new ResponseEntity<>(collectionList, HttpStatus.OK);
    }

    //   添加一条收藏歌曲
    @PostMapping("")
    public ResponseEntity addCollectionMusic(@RequestBody Collection collection) {
        Long userId = collection.getUserId();
        Long musicId = collection.getMusicId();
        Music music = musicRepository.findOne(musicId);
        music.setCollected(true);
        musicRepository.save(music);

        collection.setCount((long) 20);
        collection.setLove(new Random().nextInt(5));
        collectionRepository.save(collection);
        //生成用户口味
        UserStyle userStyle = userStyleRepository.findByUserId(userId);
        if (userStyle == null) {
            userStyle = new UserStyle(userId, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }

        List<Collection> collection1 = collectionRepository.findByUserId(userId);
        for (Collection c : collection1) {
            String type = musicRepository.findOne(c.getMusicId()).getType();
            //设置userStyle
            switch (type) {
                case "rap":
                    userStyle.setRap(userStyle.getRap() + 0.5);
                    break;
                case "blues":
                    userStyle.setBlues(userStyle.getBlues() + 0.5);
                    break;
                case "classical":
                    userStyle.setClassical(userStyle.getClassical() + 0.5);
                    break;
                case "folk":
                    userStyle.setFolk(userStyle.getFolk() + 0.5);
                    break;
                case "heavyMetal":
                    userStyle.setHeavyMetal(userStyle.getHeavyMetal() + 0.5);
                    break;
                case "hiphop":
                    userStyle.setHiphop(userStyle.getHiphop() + 0.5);
                    break;
                case "jazz":
                    userStyle.setJazz(userStyle.getJazz() + 0.5);
                    break;
                case "light":
                    userStyle.setLight(userStyle.getLight() + 0.5);
                    break;
                case "pop":
                    userStyle.setPop(userStyle.getPop() + 0.5);
                    break;
                case "rock":
                    userStyle.setRock(userStyle.getRock() + 0.5);
                    break;
            }
            userStyleRepository.save(userStyle);
        }
        return new ResponseEntity<>("1", HttpStatus.CREATED);
    }

    //   取消收藏歌曲
    @DeleteMapping("/undo")
    public ResponseEntity deleteCollectionMusic(@RequestParam("userId") Long userId, @RequestParam("musicId") Long musicId) {
        Music music = musicRepository.findOne(musicId);
        music.setCollected(false);
        musicRepository.save(music);
        collectionRepository.deleteByMusicIdAndUserId(musicId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}