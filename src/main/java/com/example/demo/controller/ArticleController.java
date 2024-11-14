package com.example.demo.controller;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Result;
//import com.example.demo.utils.JwtUtil;
//import jakarta.servlet.http.HttpServletResponse;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestHeader;


//import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public <T> Result<T> add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            int pageNum,
            int pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> Pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(Pb);
    }

    @GetMapping("/detail")
    public Result<Article> detail(int id){
        Article article = articleService.findById(id);
        if (article == null){
            return Result.error("Article not found");
        }
        return Result.success(article);
    }

    @PutMapping
    public <T> Result<T> update(@RequestBody @Validated(Article.Update.class) Article article){
        // Check if the article with the given id exists
        if (articleService.findById(article.getId()) == null) {
            return Result.error("Article not found");
        }
        if (categoryMapper.findById(article.getCategoryId()) == null) {
            return Result.error("Category not found");
        }
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public <T> Result<T> delete(int id){
        if (articleService.findById(id) == null) {
            return Result.error("Article not found");
        }
        articleService.delete(id);
        return Result.success();
    }
}