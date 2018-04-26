package com.bs.controller;

import com.bs.entity.Comment;
import com.bs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

//   添加一条评论
    @PostMapping("")
    public ResponseEntity addComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return ResponseEntity.ok().build();
    }

//   获取当前歌曲的评论
    @GetMapping("/{musicId}")
    public ResponseEntity getMusicComment(@PathVariable("musicId") Long musicId) {
//        List<Comment> commentList = commentRepository.findByMusicIdOrderByLoveCount(musicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//   给评论点赞
    @PutMapping("/{commentId}")
    public ResponseEntity likeComment(@PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findOne(commentId);
//        comment.setLoveCount(comment.getLoveCount()+1);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//  取消点赞
//  删除评论

}
