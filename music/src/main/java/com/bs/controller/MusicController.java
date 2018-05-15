package com.bs.controller;

import com.bs.entity.*;
import com.bs.entity.Collection;
import com.bs.exceptions.BusinessException;
import com.bs.repository.*;
import com.bs.service.MusicCenterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private SignerLeaderRepository signerLeaderRepository;

    @Autowired
    private UserStyleRepository userStyleRepository;

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
                saveDate(findMusic);
            }
            List<Music> music1 = musicRepository.findByMusicContaining(musicName);
            return new ResponseEntity<>(getMusicName(music1, musicInfo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getMusicName(musics, musicInfo), HttpStatus.OK);
        }
    }

    private Singer initSinger(Map music){
        String signerName = (String) music.get("artist");
        Singer newSinger = signerRepository.findBySinger(signerName);

        //如果没有该歌手，则保存,否则直接保存音乐
        if (newSinger == null) {
            newSinger = new Singer();

            //设置歌手类型
            List<String> typeList = Arrays.asList("rap", "blues", "classical", "folk", "heavyMetal", "hiphop", "jazz", "light", "pop", "rock");

            //添加歌手
            newSinger.setTag(typeList.get(new Random().nextInt(10)));
            newSinger.setSinger(signerName);
            signerRepository.save(newSinger);

            //添加歌手排行榜
            SingerLeaderBoard singerLeaderBoard = new SingerLeaderBoard();
            singerLeaderBoard.setSingerId(newSinger.getId());
            singerLeaderBoard.setHot((long) new Random().nextInt(1000));
            singerLeaderBoard.setTag(newSinger.getTag());
            signerLeaderRepository.save(singerLeaderBoard);
        }
        return newSinger;
    }

    private Music initMusic(Map music,Singer singer){
        Music newMusic = new Music();
        newMusic.setMusic((String) music.get("music"));
        newMusic.setAlbum((String) music.get("album"));
        newMusic.setSingerId(singer.getId());
        newMusic.setType(singer.getTag());
        newMusic.setCollected(false);
        musicRepository.save(newMusic);
        return newMusic;
    }

    private void initMusicLeaderBoard(Music music,Singer singer){
        MusicLeaderBoard newMusicLeaderBoard = new MusicLeaderBoard();
        newMusicLeaderBoard.setSingerId(singer.getId());
        newMusicLeaderBoard.setType(singer.getTag());
        newMusicLeaderBoard.setMusicId(music.getId());
        newMusicLeaderBoard.setHot((long) new Random().nextInt(10000));
        musicLeaderRepository.save(newMusicLeaderBoard);
    }

    private void initComment(List<Map> comments, Music newMusic) {
        for (Map comment : comments) {
            Comment comment1 = new Comment();
            comment1.setContent((String) comment.get("content"));
            comment1.setCount((Integer) comment.get("likedcount"));
            comment1.setNickName((String) comment.get("nickname"));
            comment1.setImage((String) comment.get("avatarurl"));
            comment1.setLike(false);
            comment1.setMusicId(newMusic.getId());
            commentRepository.save(comment1);
        }
    }

    private void initMusicStyle(Music newMusic) {
        //初始化音乐类型type
        MusicStyle newMusicStyle = new MusicStyle();
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

    //格式化只返回歌名
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

    //找到数据存入mysql
    private void saveDate(List<Map> findMusic){
        for (Map music : findMusic) {

            //添加歌手
            Singer newSinger = initSinger(music);

            //添加一条music
            Music newMusic = initMusic(music,newSinger);

            //添加评论
            List<Map> comments = (List<Map>) music.get("comments");
            initComment(comments, newMusic);

            //添加一条排行榜
            initMusicLeaderBoard(newMusic,newSinger);

            //添加一条音乐类型
            initMusicStyle(newMusic);
        }
    }

    //根据音乐Id获取信息
    @GetMapping("/{id}")
    public ResponseEntity getOneMusic(@PathVariable("id") Long id) throws BusinessException {
        Music music = musicRepository.findOne(id);

        //设置播放量 +1

        String signer = signerRepository.findOne(music.getSingerId()).getSinger();
        List<Comment> comments = commentRepository.findByMusicIdOrderByCountDesc(id);
        Map musicInfo = new HashMap();

        musicInfo.put("music", music);
        musicInfo.put("singer", signer);
        musicInfo.put("comment", comments);

        return new ResponseEntity<>(musicInfo, HttpStatus.OK);
    }

    private int getSize(List array) {
        int size = 10;
        if (array.size() < size) {
            size = array.size();
        }
        return size;
    }

    private double formatRandom() {
        return Math.round(new Random().nextDouble() * 100) / 100.0;
    }


    //    推荐歌曲
    @GetMapping("/recommend/{userId}")
    public ResponseEntity recommendMusic(@PathVariable("userId") Long userId) throws BusinessException {
        List<Collection> collectionsList = collectionRepository.findByUserId(userId);
        if (collectionsList == null) {
            throw new BusinessException("还没有收藏音乐,请先收藏");
        }
        List<Collection> collections = collectionRepository.findByUserIdOrderByLoveDesc(userId);

        //找到自己评分高的50音乐
        List<Long> myLoveMusicIdList = collections.subList(0, getSize(collections)).stream().map(Collection::getMusicId).collect(Collectors.toList());

        //根据50首歌曲找到相应歌手Id， 找到出现次数最多的歌手id
        List<Long> myLoveSignerIdList = myLoveMusicIdList.stream().map(id -> musicRepository.findOne(id).getSingerId()).collect(Collectors.toList());
        List countList = new ArrayList<>();
        myLoveSignerIdList.stream().forEach(id -> countList.add(Collections.frequency(myLoveSignerIdList, id)));
        Collections.sort(countList, Collections.reverseOrder());
        Integer count = (Integer) countList.get(0);
        Long mostLoveSignerId = myLoveSignerIdList.stream().filter(id -> Collections.frequency(myLoveSignerIdList, id) == count).collect(Collectors.toList()).get(0);

        //找到最喜欢的歌手
        Singer signer = signerRepository.findOne(mostLoveSignerId);
        String mostLovedSignerTag = signer.getTag();

        //2 歌手类别(根据喜欢的歌手类别找音乐)
        List<MusicLeaderBoard> myLoveTagList = musicLeaderRepository.findByTypeOrderByHotDesc(mostLovedSignerTag);
        ////从歌曲排行榜中找到该类别的前50首歌曲,得到音樂ID,
        List<Long> secondMusics = myLoveTagList.subList(0, getSize(myLoveTagList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        // 3类似歌手的歌曲类别
        //从歌手排行榜中找到该类别的第一位歌手
        Long signerId = myLoveTagList.get(0).getSingerId();

        //找到类似tag的第一位歌手的getSize()首音乐
        List<MusicLeaderBoard> signerMusicList = musicLeaderRepository.findBySingerIdOrderByHotDesc(signerId);
        List<Long> thirdMusics = signerMusicList.subList(0, getSize(signerMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        // 4 播放量最高的音乐排行
        //将播放量最高的音乐作为基本音乐
        List<Collection> myLoveMusicList = collectionRepository.findByUserIdOrderByCountDesc(userId);
        Long mostLoveMusicId = myLoveMusicList.get(0).getMusicId();
        Music music = musicRepository.findOne(mostLoveMusicId);

        //找到歌手的所有歌曲按热度排序,得到前50首
        List<MusicLeaderBoard> playCountMusicList = musicLeaderRepository.findBySingerIdOrderByHotDesc(music.getSingerId());
        List<Long> fourMusics = playCountMusicList.subList(0, getSize(playCountMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        //5 基本音乐的type排行
        //找到该类型的所有歌曲按热度排序
        String type = music.getType();
        List<MusicLeaderBoard> typeMusicList = musicLeaderRepository.findByTypeOrderByHotDesc(type);
        List<Long> fiveMusics = typeMusicList.subList(0, getSize(typeMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        //6 热评
        List<Comment> comments = commentRepository.findByMusicIdOrderByCountDesc(music.getId());
        //热评的人
        Long commentId = comments.get(0).getUserId();
        //根据播放量找到此人最喜欢的50首音乐
        List<Collection> hotCommentPeopleMusicList = collectionRepository.findByUserIdOrderByCountDesc(commentId);
        List<Long> sixMusics = hotCommentPeopleMusicList.subList(0, getSize(hotCommentPeopleMusicList)).stream().map(Collection::getMusicId).collect(Collectors.toList());


        //我的歌單
        //7 选择10首基本音乐，选出同样喜欢这些音乐的人，找到他们喜欢的音乐

        List<Long> tenBasicMusicIdList = myLoveMusicList.subList(0, getSize(myLoveMusicList)).stream().map(Collection::getMusicId).collect(Collectors.toList());

        //每首歌选择最喜欢的5个人，一共50个人,存在数组mostLikeMusicUserId中，找出这些人中出现次数最多的
//        tenBasicMusicIdList.stream().forEach(id -> collectionRepository.findByMusicIdOrderByCountDesc(id));
        List<Long> mostLikeMusicUserIdList = new ArrayList<>();
        tenBasicMusicIdList.forEach(id -> {
            List<Collection> similarCollectionList = collectionRepository.findByMusicIdOrderByCountDesc(id);
            List<Long> temp = similarCollectionList.subList(0, getSize(similarCollectionList)).stream().map(Collection::getUserId).collect(Collectors.toList());
            mostLikeMusicUserIdList.addAll(temp);
        });

        //最相似用户
        List<Long> userIdList = new ArrayList<>();

        mostLikeMusicUserIdList.stream().forEach(id -> userIdList.add((long) Collections.frequency(mostLikeMusicUserIdList, id)));
        Collections.sort(userIdList, Collections.reverseOrder());
        Integer likeUserId = Math.toIntExact(userIdList.get(0));
        Long mostLikeUserId = myLoveSignerIdList.stream().filter(id -> Collections.frequency(mostLikeMusicUserIdList, id) == likeUserId).collect(Collectors.toList()).get(0);

        //找到该人最喜欢的50首音乐
        List<Collection> mostSimilarUserList = collectionRepository.findByUserIdOrderByCountDesc(mostLikeUserId);
        List<Long> sevenMusics = mostSimilarUserList.subList(0, getSize(mostSimilarUserList)).stream().map(Collection::getMusicId).collect(Collectors.toList());


        List<Long> allMusicList = new ArrayList<>();

        allMusicList.addAll(secondMusics);
        allMusicList.addAll(thirdMusics);
        allMusicList.addAll(fourMusics);
        allMusicList.addAll(fiveMusics);
        allMusicList.addAll(sixMusics);
        allMusicList.addAll(sevenMusics);

        List<Collection> myCollection = collectionRepository.findByUserId(userId);
        List<Long> myCollectionMusicId = myCollection.stream().map(Collection::getMusicId).collect(Collectors.toList());

        //再把已经是自己歌单里面的删除,存入recommendMusicIdList
        List<Long> recommendMusicIdList = new ArrayList();
        allMusicList.stream().distinct().forEach(musicId -> {
            List list = myCollectionMusicId.stream().filter(id -> id == musicId).collect(Collectors.toList());
            if (list.size() == 0) {
                recommendMusicIdList.add(musicId);
            }
        });

        //潜在因子,根据用户的口味，以及歌曲的风格
        //张三对一首音乐的喜欢程度，存放在一个数组对象里面
        List<Map> myLikeDegreeList = new ArrayList<>();
        List subMyLikeDegreeList = new ArrayList<>();
        List<Long> arrayList = new ArrayList<>();

        for (Long musicId : recommendMusicIdList) {
            Map myLikeDegreeMap = new HashMap();

            MusicStyle musicStyle = musicStyleRepository.findByMusicId(musicId);

            double rap = musicStyle.getRap();
            double blues = musicStyle.getBlues();
            double classical = musicStyle.getClassical();
            double folk = musicStyle.getFolk();
            double heavyMetal = musicStyle.getHeavyMetal();
            double hiphop = musicStyle.getHiphop();
            double jazz = musicStyle.getJazz();
            double light = musicStyle.getLight();
            double pop = musicStyle.getPop();
            double rock = musicStyle.getRock();

            UserStyle userStyle = userStyleRepository.findByUserId(userId);

            double myRap = userStyle.getRap();
            double myBlues = userStyle.getBlues();
            double myClassical = userStyle.getClassical();
            double myFolk = userStyle.getFolk();
            double myHeavyMetal = userStyle.getHeavyMetal();
            double myHiphop = userStyle.getHiphop();
            double myJazz = userStyle.getJazz();
            double myLight = userStyle.getLight();
            double myPop = userStyle.getPop();
            double myRock = userStyle.getRock();

            double myLikeMusicDegree = myRap * rap + myBlues * blues + myClassical * classical +
                    myFolk * folk + myHeavyMetal * heavyMetal + myHiphop * hiphop +
                    myJazz * jazz + myLight * light + myPop * pop + myRock * rock;

            myLikeDegreeMap.put("musicId", musicId);
            myLikeDegreeMap.put("degree", myLikeMusicDegree);
            myLikeDegreeList.add(myLikeDegreeMap);
            subMyLikeDegreeList.add(myLikeMusicDegree);
        }

        // 从myLikeDegreeList里面找到degree最高的前10个推荐给该用户

        Collections.sort(subMyLikeDegreeList, Collections.reverseOrder());

        subMyLikeDegreeList.subList(0, getSize(subMyLikeDegreeList));
        subMyLikeDegreeList.stream().forEach(m -> {
            List<Map> mapList = myLikeDegreeList.stream().filter(map -> m.equals(map.get("degree"))).collect(Collectors.toList());
            arrayList.add((Long) mapList.get(0).get("musicId"));
        });

        List musicList = new ArrayList<>();
        for (Long id : arrayList) {
            Music music1 = musicRepository.findOne(id);
            ObjectMapper oMapper = new ObjectMapper();
            Map data = oMapper.convertValue(music1, Map.class);
            Singer singer = signerRepository.findOne(music1.getSingerId());
            data.put("singer", singer.getSinger());
            musicList.add(data);
        }

        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }
}

//myCollection：我的歌单列表
//myCollectionMusicId：我的歌单musicId [id,id,,,]
//collections：我的歌单评分列表 array
//collections1：我的歌单播放量列表
//collections2：热评得到的类似的人的歌单播放量列表
//collections3：这10首音乐的播放量列表
//mostPlayMusicArray：我的歌单播放量最高的10首音乐
//mostLikeMusicUserId：喜欢播放这10首音乐的前50个人
//mostLikeMusicUserIdArray：统计出现次数最多的人[mostLikeUserMap:{userId,count},{},,]
//collections4：播放10首歌得到类似的人的歌单播放量列表
//signers：我喜欢的歌手列表[singerMap:{signerId,count},{}]
//music：基本音乐
//comments：基本音乐的热评
//firstMusics：自己评分高的50音乐 array[id,id,,,]
//secondMusics:最喜欢的歌手的类别评分高的50音乐
//thirdMusics：类似歌手该类别评分高的50音乐
//fourMusics：基本音乐的歌手评分高的50音乐
//fiveMusics：基本音乐类别评分高的50音乐
//sixMusics：热评类似的人播放量高的50音乐
//sevenMusics：10首歌类似的人播放量高的50音乐
//array：所有可能的350首音乐
//arrayMap:要推荐给用户的若干歌曲，除去自己列表中有的,里面存的是[{musicId,count}] ?
//userStyleList :用户口味[userStyle{"rap",100},{"pop":10}]
//myLikeDegreeList:我对该音乐的喜欢程度[myLikeDegreeMap{musicId,degree}]


