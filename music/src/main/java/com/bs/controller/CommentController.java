package com.bs.controller;

import com.bs.entity.Comment;
import com.bs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    //   添加一条评论
    @PostMapping("")
    public ResponseEntity addComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return new ResponseEntity<>("1", HttpStatus.CREATED);
    }

    //   获取我所有的评论
    @GetMapping("/{userId}")
    public ResponseEntity getMusicComment(@PathVariable("userId") Long userId) {
        List<Comment> commentList = commentRepository.findAllByUserId(userId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //  给评论点赞
    @PutMapping("/{commentId}")
    public ResponseEntity likeComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setCount(comment.getCount() + 1);
        comment.setLike(true);
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  取消评论点赞
    @PutMapping("/undo/ {commentId}")
    public ResponseEntity unlikeComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setCount(comment.getCount() - 1);
        comment.setLike(false);
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
