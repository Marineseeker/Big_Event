package com.example.demo.service;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;

public interface ArticleService {

    void add(Article article);

    PageBean<Article> list(int pageNum, int pageSize, Integer categoryId, String state);

    Article findById(int id);

    void update(Article article);

    void delete(int id);
}
