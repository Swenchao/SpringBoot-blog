package com.sc.web.admin;

import com.sc.po.Blog;
import com.sc.po.User;
import com.sc.service.BlogService;
import com.sc.service.TagService;
import com.sc.service.TypeService;
import com.sc.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    // 三个不同页面（博客发布 博客列表 博客重定向）

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    // @PageableDefault指定分页参数：size每页几条数据，sort排序方式，direction排序方向
    // 博客列表

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    // 博客按条件搜索

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        // 返回到admin下面的blogs页面的blogList片段中（局部渲染）
        return "admin/blogs :: blogList";
    }

    // 博客发布

    @GetMapping("/blogs/input")
    public String input(Model model) {
        // 生成type tag 和 博客返回给前端页面
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    // 新增和修改共用一个方法
    // session其中有user，为了后面增加作者

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
//        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
//        System.out.println(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    // 修改博客
    // @PathVariable从地址获取id

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }


//    @GetMapping("/blogs/{id}/delete")
//    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
//        System.out.println("///////////////删除///////////////");
//        System.out.println(id);
//        blogService.deleteBlog(id);
//        attributes.addFlashAttribute("message", "删除成功");
//        return REDIRECT_LIST;
//    }
}
