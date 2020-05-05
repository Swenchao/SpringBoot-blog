package com.sc.service;

import com.sc.po.Comment;

import java.util.List;


public interface CommentService {

    // 列出评论

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
