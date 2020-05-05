package com.sc.service;

import com.sc.po.Blog;
import com.sc.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    // 查询博客

    Blog getBlog(Long id);

    // 取得博客内容并转化成md格式

    Blog getAndConvert(Long id);

    // 分页查询

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    // 根据标签id查询博客

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    // 搜索查询

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    // 归档 String年份，list博客列表

    Map<String,List<Blog>> archiveBlog();

    // 博客总数

    Long countBlog();

    // 新增博客

    Blog saveBlog(Blog blog);

    // 根据id查到博客，赋值于新的内容

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
