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
        comment.setLike(false);
        comment.setCount(0);
        commentRepository.save(comment);
        return new ResponseEntity<>("评论成功", HttpStatus.CREATED);
    }

    //   获取我所有的评论
    @GetMapping("/{userId}")
    public ResponseEntity getMusicComment(@PathVariable("userId") Long userId) {
        List<Comment> commentList = commentRepository.findAllByUserIdOrderByCreateTime(userId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //  给评论点赞
    @PutMapping("")
    public ResponseEntity likeComment(@RequestParam("id") Long id) {
        Comment comment = commentRepository.findOne(id);
        comment.setCount(comment.getCount() + 1);
        comment.setLike(true);
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  取消评论点赞
    @PutMapping("/undo")
    public ResponseEntity unlikeComment(@RequestParam("id") Long id) {
        Comment comment = commentRepository.findOne(id);
        comment.setCount(comment.getCount() - 1);
        comment.setLike(false);
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  删除评论
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Long id) {
        commentRepository.delete(id);
        if (commentRepository.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
