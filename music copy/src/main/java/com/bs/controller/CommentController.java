package com.bs.controller;

import com.bs.entity.Comment;
import com.bs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        List<Comment> commentList = commentRepository.findByMusicIdOrderByLoveCount(musicId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //  给评论点赞
    @PutMapping("/{commentId}")
    public ResponseEntity likeComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setLoveCount(comment.getLoveCount() + 1);
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  删除评论
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentRepository.delete(commentId);
        if (commentRepository.findOne(commentId) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
