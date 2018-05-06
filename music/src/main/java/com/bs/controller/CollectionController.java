package com.bs.controller;

import com.bs.entity.*;
import com.bs.entity.Collection;
import com.bs.exceptions.BusinessException;
import com.bs.repository.*;
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
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

    //   添加一条收藏歌曲
    @PostMapping("")
    public ResponseEntity addCollectionMusic(@RequestBody Collection collection) {
        Music music = musicRepository.findOne(collection.getMusicId());
        music.setCollected(true);
        musicRepository.save(music);
        collectionRepository.save(collection);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //   删除一条收藏歌曲
    @DeleteMapping("/{musicId}")
    public ResponseEntity deleteCollectionMusic(@PathVariable("musicId") Long id) {
        Music music = musicRepository.findOne(id);
        music.setCollected(false);
        musicRepository.save(music);
        collectionRepository.deleteByMusicId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    评分 0-5
    @PutMapping("/collection")
    public ResponseEntity musicScore(@RequestBody Collection collection) {
        Collection collection1 = collectionRepository.findOne(collection.getId());
        collection1.setLove(collection.getLove());
        collectionRepository.save(collection1);

        MusicLeaderBoard musicLeaderBoard = musicLeaderRepository.findByMusicId(collection.getMusicId());
        musicLeaderBoard.setHot(musicLeaderBoard.getHot() + collection.getLove()); //根据musicId 设置排行榜

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private int getSize(List array){
        int size = 10;
        if(array.size() < size){
            size = array.size();
        }
        return size;
    }

    //    推荐歌曲
    @GetMapping("/recommend/{userId}")
    public ResponseEntity recommendMusic(@PathVariable("userId") Long userId) throws BusinessException{
        List<Collection> collectionsList = collectionRepository.findAll();
        if(collectionsList==null){
            throw new BusinessException("还没有收藏音乐");
        }
        List<Collection> collections = collectionRepository.findByUserIdOrderByLove(userId);

        //找到自己评分高的50音乐
        List<Long> myLoveMusicIdList = collections.subList(0, getSize(collections)).stream().map(Collection::getMusicId).collect(Collectors.toList());

        //根据50首歌曲找到相应歌手Id， 找到出现次数最多的歌手id
        List<Long> myLoveSignerIdList = myLoveMusicIdList.stream().map(id -> musicRepository.findOne(id).getSingerId()).collect(Collectors.toList());
        List countList = new ArrayList<>();
        myLoveSignerIdList.stream().forEach(id -> countList.add(Collections.frequency(myLoveSignerIdList, id)));
        Collections.sort(countList);
        Integer count = (Integer) countList.get(0);
        Long mostLoveSignerId = myLoveSignerIdList.stream().filter(id -> Collections.frequency(myLoveSignerIdList, id) == count).collect(Collectors.toList()).get(0);

        //找到最喜欢的歌手
        Singer signer = signerRepository.findOne(mostLoveSignerId);
        String mostLovedSignerTag = signer.getTag();

        //2 歌手类别(根据喜欢的歌手类别找音乐)
        List<MusicLeaderBoard> myLoveTagList = musicLeaderRepository.findByTagOrderByHot(mostLovedSignerTag);
        ////从歌曲排行榜中找到该类别的前50首歌曲,得到音樂ID,
        List<Long> secondMusics = myLoveTagList.subList(0, getSize(myLoveTagList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        // 3类似歌手的歌曲类别
        //从歌手排行榜中找到该类别的第一位歌手
        Long signerId = myLoveTagList.get(0).getSingerId();

        //找到类似tag的第一位歌手的getSize(myL)首音乐
        List<MusicLeaderBoard> signerMusicList = musicLeaderRepository.findBySingerIdOrderByHot(signerId);
        List<Long> thirdMusics = signerMusicList.subList(0, getSize(signerMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        // 4 播放量最高的音乐排行
        //将播放量最高的音乐作为基本音乐
        List<Collection> myLoveMusicList = collectionRepository.findByUserIdOrderByCount(userId);
        Long mostLoveMusicId = myLoveMusicList.get(0).getMusicId();
        Music music = musicRepository.findOne(mostLoveMusicId);

        //找到歌手的所有歌曲按热度排序,得到前50首
        List<MusicLeaderBoard> playCountMusicList = musicLeaderRepository.findBySingerIdOrderByHot(music.getSingerId());
        List<Long> fourMusics = playCountMusicList.subList(0, getSize(playCountMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        //5 基本音乐的type排行
        //找到该类型的所有歌曲按热度排序
        String type = music.getType();
        List<MusicLeaderBoard> typeMusicList = musicLeaderRepository.findByTypeOrderByHot(type);
        List<Long> fiveMusics = typeMusicList.subList(0, getSize(typeMusicList)).stream().map(MusicLeaderBoard::getMusicId).collect(Collectors.toList());


        //6 热评
        List<Comment> comments = commentRepository.findByMusicIdOrderByCountDesc(music.getId());
        //热评的人
        Long commentId = comments.get(0).getUserId();
        //根据播放量找到此人最喜欢的50首音乐
        List<Collection> hotCommentPeopleMusicList = collectionRepository.findByUserIdOrderByCount(commentId);
        List<Long> sixMusics = hotCommentPeopleMusicList.subList(0, getSize(hotCommentPeopleMusicList)).stream().map(Collection::getMusicId).collect(Collectors.toList());


        //我的歌單
        //7 选择10首基本音乐，选出同样喜欢这些音乐的人，找到他们喜欢的音乐

        List<Long> tenBasicMusicIdList = myLoveMusicList.subList(0, 10).stream().map(Collection::getMusicId).collect(Collectors.toList());

        //每首歌选择最喜欢的5个人，一共50个人,存在数组mostLikeMusicUserId中，找出这些人中出现次数最多的
        tenBasicMusicIdList.stream().forEach(id -> collectionRepository.findByMusicIdOrderByCount(id));
        List<Long> mostLikeMusicUserIdList = new ArrayList<>();
        tenBasicMusicIdList.forEach(id -> {
            List<Collection> fivePeople = collectionRepository.findByMusicIdOrderByCount(id);
            List<Long> temp = fivePeople.subList(0, 5).stream().map(Collection::getUserId).collect(Collectors.toList());
            mostLikeMusicUserIdList.addAll(temp);
        });

        //最相似用户
        List<Long> userIdList = new ArrayList<>();

        mostLikeMusicUserIdList.stream().forEach(id -> userIdList.add((long) Collections.frequency(mostLikeMusicUserIdList, id)));
        Collections.sort(userIdList);
        Integer likeUserId = Math.toIntExact(userIdList.get(0));
        Long mostLikeUserId = myLoveSignerIdList.stream().filter(id -> Collections.frequency(mostLikeMusicUserIdList, id) == likeUserId).collect(Collectors.toList()).get(0);

        //找到该人最喜欢的50首音乐
        List<Collection> mostSimilarUser = collectionRepository.findByUserIdOrderByCount(mostLikeUserId);
        List<Long> sevenMusics = mostSimilarUser.subList(0, getSize(mostSimilarUser)).stream().map(Collection::getMusicId).collect(Collectors.toList());


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


        //生成用户口味，遍历歌单myCollectionMusicId，从MusicStyle里面，找出这首歌最大的type，作为这首歌的type
        // ，给该用户的type+1

        UserStyle userStyle = userStyleRepository.findByUserId(userId);
        if (userStyle == null) {
            userStyle = new UserStyle(userId, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        for (Long musicId : myCollectionMusicId) {
//            List<Integer> typeList = new ArrayList<>();


            MusicStyle music1 = musicStyleRepository.findByMusicId(musicId);
            Double rap = music1.getRap();
            Double blues = music1.getBlues();
            Double classical = music1.getClassical();
            Double folk = music1.getFolk();
            Double heavyMetal = music1.getHeavyMetal();
            Double hiphop = music1.getHiphop();
            Double jazz = music1.getJazz();
            Double light = music1.getLight();
            Double pop = music1.getPop();
            Double rock = music1.getRock();

//            typeList.add(rap);
//            typeList.add(blues);
//            typeList.add(classical);
//            typeList.add(folk);
//            typeList.add(heavyMetal);
//            typeList.add(hiphop);
//            typeList.add(jazz);
//            typeList.add(light);
//            typeList.add(pop);
//            typeList.add(rock);

            //得到该首歌的口味

            List<Double> typeList = Arrays.asList(rap, blues, classical, folk, heavyMetal, hiphop, jazz, light, pop, rock);
            Collections.sort(typeList);
            Double max = typeList.get(0);
            //todo 得到名字

            //设置musicStyle
            Music musicInfo = musicRepository.findOne(musicId);
            musicInfo.setType("1111");

            //设置userStyle
            switch ("1") {
                case "rap":
                    userStyle.setRap(userStyle.getRap() + 1);
                    break;
                case "blues":
                    userStyle.setBlues(userStyle.getBlues() + 1);
                    break;
                case "classical":
                    userStyle.setClassical(userStyle.getClassical() + 1);
                    break;
                case "folk":
                    userStyle.setFolk(userStyle.getFolk() + 1);
                    break;
                case "heavyMetal":
                    userStyle.setHeavyMetal(userStyle.getHeavyMetal() + 1);
                    break;
                case "hiphop":
                    userStyle.setHiphop(userStyle.getHiphop() + 1);
                    break;
                case "jazz":
                    userStyle.setJazz(userStyle.getJazz() + 1);
                    break;
                case "light":
                    userStyle.setLight(userStyle.getLight() + 1);
                    break;
                case "pop":
                    userStyle.setPop(userStyle.getPop() + 1);
                    break;
                case "rock":
                    userStyle.setRock(userStyle.getRock() + 1);
                    break;
            }
            List array = new ArrayList<>();


            //潜在因子,根据用户的口味，以及歌曲的风格
            //张三对一首音乐的喜欢程度，存放在一个数组对象里面
            List<Map> myLikeDegreeList = new ArrayList<>();
            for (Long musicId1 : recommendMusicIdList) {
                Map myLikeDegreeMap = new HashMap();

                Double rap1 = music1.getRap();
                Double blues1 = music1.getBlues();
                Double classical1 = music1.getClassical();
                Double folk1 = music1.getFolk();
                Double heavyMetal1 = music1.getHeavyMetal();
                Double hiphop1 = music1.getHiphop();
                Double jazz1 = music1.getJazz();
                Double light1 = music1.getLight();
                Double pop1 = music1.getPop();
                Double rock1 = music1.getRock();

                UserStyle userStyleInfo = userStyleRepository.findByUserId(userId);


                Integer myRap = userStyleInfo.getRap();
                Integer myBlues = userStyleInfo.getBlues();
                Integer myClassical = userStyleInfo.getClassical();
                Integer myFolk = userStyleInfo.getFolk();
                Integer myHeavyMetal = userStyleInfo.getHeavyMetal();
                Integer myHiphop = userStyleInfo.getHiphop();
                Integer myJazz = userStyleInfo.getJazz();
                Integer myLight = userStyleInfo.getLight();
                Integer myPop = userStyleInfo.getPop();
                Integer myRock = userStyleInfo.getRock();

                Double myLikeMusic = myRap * rap1 + myBlues * blues1 + myClassical * classical1 +
                        myFolk * folk1 + myHeavyMetal * heavyMetal1 + myHiphop * hiphop1 +
                        myJazz * jazz1 + myLight * light1 + myPop * pop1 + myRock * rock1;

                myLikeDegreeMap.put("musicId", musicId1);
                myLikeDegreeMap.put("degree", myLikeMusic);
                myLikeDegreeList.add(myLikeDegreeMap);
            }

            // todo 从myLikeDegreeList里面找到degree最高的前五个推荐给该用户
        }
        return new ResponseEntity<>(mostLikeMusicUserIdList, HttpStatus.OK);
    }
}

//myCollection：我的歌单列表
//myCollectionMusicId：我的歌单musicId [id,id,,,]
//collections：我的歌单评分列表 array
//collections11：我的歌单播放量列表
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
