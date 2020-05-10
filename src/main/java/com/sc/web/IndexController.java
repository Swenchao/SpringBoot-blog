package com.sc.web;

import com.sc.service.BlogService;
import com.sc.service.TagService;
import com.sc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        // 博客
        model.addAttribute("page",blogService.listBlog(pageable));
        // 分类
        model.addAttribute("types", typeService.listTypeTop(8));
        // 标签
        model.addAttribute("tags", tagService.listTagTop(5));
        // 推荐博客
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(5));
        // 返回到 index页面
        return "index";
    }

    // @RequestParam String query拿到请求的值

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        // 模糊查询select * from t_blog where title like '%内容%'
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        // 返回给前端查询的内容
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
