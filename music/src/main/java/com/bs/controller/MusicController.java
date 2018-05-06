package com.bs.controller;

import com.bs.entity.*;
import com.bs.exceptions.BusinessException;
import com.bs.repository.*;
import com.bs.service.MusicCenterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SignerRepository signerRepository;

    @Autowired
    private MusicLeaderRepository musicLeaderRepository;

    @Autowired
    private MusicStyleRepository musicStyleRepository;

    @Autowired
    private MusicCenterService musicCenterService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;


    //搜索时从本地获取返回歌名
    public List<HashMap> getMusicName(List<Music> music, List<HashMap> musicInfo) {
        music.forEach(m -> {

            String musicName1 = m.getMusic();
            String signer = signerRepository.findOne(m.getSingerId()).getSinger();
            String info = musicName1 + '-' + signer;
            HashMap map = new HashMap();
            map.put("id", m.getId());
            map.put("info", info);
            musicInfo.add(map);
        });
        return musicInfo;
    }

    //搜索时模糊匹配歌曲
    @GetMapping("")
    public ResponseEntity getMusicContaining(@RequestParam("musicName") String musicName) throws BusinessException {
        List<Music> musics = musicRepository.findByMusicContaining(musicName);
        List<HashMap> musicInfo = new ArrayList<>();

        if (musics.size() == 0) {
            List<Map> findMusic = musicCenterService.findMusic(musicName);
            if (findMusic.size() == 0) {
                throw new BusinessException("没有找到该音乐");
            } else {
                for (Map music1 : findMusic) {
                    Music newMusic = new Music();
                    Singer newSigner = new Singer();
                    MusicLeaderBoard newMusicLeaderBoard = new MusicLeaderBoard();
                    MusicStyle newMusicStyle = new MusicStyle();

                    newMusic.setMusic((String) music1.get("music"));
                    newMusic.setAlbum((String) music1.get("album"));
                    newMusic.setCollected(false);

                    String signerName = (String) music1.get("artist");
                    Singer isHaveSigner = signerRepository.findBySinger(signerName);

                    //如果没有该歌手，则保存,否则直接保存音乐
                    if (isHaveSigner == null) {
                        newSigner.setSinger(signerName);
                        //设置歌手类型
                        List<String> typeList = Arrays.asList("rap", "blues", "classical", "folk", "heavyMetal", "hiphop", "jazz", "light", "pop", "rock");
                        newSigner.setTag(typeList.get(new Random().nextInt(10)));

                        //添加一条歌手
                        signerRepository.save(newSigner);

                        //添加歌手排行榜
                        SingerLeaderBoard singerLeaderBoard = new SingerLeaderBoard();
                        singerLeaderBoard.setSignerId(newSigner.getId());
                        singerLeaderBoard.setHot((long)0);
                        singerLeaderBoard.setTag(newSigner.getTag());

                        newMusic.setSingerId(newSigner.getId());
                    } else {
                        newMusic.setSingerId(isHaveSigner.getId());
                    }
                    //添加一条music
                    musicRepository.save(newMusic);

                    //添加一条排行榜
                    newMusicLeaderBoard.setSingerId(newSigner.getId());
                    newMusicLeaderBoard.setMusicId(newMusic.getId());
                    newMusicLeaderBoard.setHot((long) 0);
                    newMusicLeaderBoard.setType(newMusic.getType());
                    musicLeaderRepository.save(newMusicLeaderBoard);

                    //初始化音乐类型type
                    newMusicStyle.setMusicId(newMusic.getId());
                    newMusicStyle.setRap(formatRandom());
                    newMusicStyle.setRock(formatRandom());
                    newMusicStyle.setPop(formatRandom());
                    newMusicStyle.setLight(formatRandom());
                    newMusicStyle.setJazz(formatRandom());
                    newMusicStyle.setHiphop(formatRandom());
                    newMusicStyle.setHeavyMetal(formatRandom());
                    newMusicStyle.setFolk(formatRandom());
                    newMusicStyle.setClassical(formatRandom());
                    newMusicStyle.setBlues(formatRandom());
                    musicStyleRepository.save(newMusicStyle);
                }
            }
            List<Music> music1 = musicRepository.findByMusicContaining(musicName);

            return new ResponseEntity<>(getMusicName(music1, musicInfo), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(getMusicName(musics, musicInfo), HttpStatus.OK);
        }
    }

    //根据音乐名称获取信息
    @GetMapping("/{id}")
    public ResponseEntity getOneMusic(@PathVariable("id") Long id) throws BusinessException {
        Music music = musicRepository.findOne(id);
        //设置播放量 +1


        String signer = signerRepository.findOne(music.getSingerId()).getSinger();
        List<Comment> comments = commentRepository.findByMusicIdOrderByCountDesc(id);

        List<Map> commentList = new ArrayList<>();
        for (Comment c : comments) {
            ObjectMapper oMapper = new ObjectMapper();
            Map data = oMapper.convertValue(c, Map.class);
            User user = userRepository.findOne(c.getUserId());
            data.put("image", user.getImage());
            data.put("username", user.getUsername());
            commentList.add(data);
        }

        Map musicInfo = new HashMap();
        musicInfo.put("music", music);
        musicInfo.put("singer", signer);
        musicInfo.put("comment", commentList);


        return new ResponseEntity<>(musicInfo, HttpStatus.OK);
    }


    //获取推荐音乐信息
    @GetMapping("/recommend/{userId}")
    public ResponseEntity recommendMusic() {
        List<Music> musics = musicRepository.findAll();
        List musicList = new ArrayList<>();
        for (Music music : musics) {
            ObjectMapper oMapper = new ObjectMapper();
            Map data = oMapper.convertValue(music, Map.class);
            Singer signer = signerRepository.findOne(music.getSingerId());

            data.put("signer", signer.getSinger());
            musicList.add(data);
        }
        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }


    private double formatRandom(){
       return Math.round(new Random().nextDouble()*100)/100.0;
    }
}
