package com.example.demo.service.impl;

import com.example.demo.mapper.ArticleMapper;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Result;
import com.example.demo.service.ArticleService;
import com.example.demo.utils.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        int userId = UserUtil.getUserId();
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(int pageNum, int pageSize, Integer categoryId, String state) {
        PageBean<Article> Pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        
        Integer userId = UserUtil.getUserId();
        List<Article> As = articleMapper.list(userId, categoryId, state);
        
        Page<Article> page = (Page<Article>) As;

        // 通过 page.getTotal() 获取总记录数
        Pb.setTotal(page.getTotal());
        // page.getResult() 获取当前页的数据列表，并设置到items属性中
        Pb.setItems(page.getResult());
        return Pb;
    }

    @Override
    public Article findById(int id) {
        return articleMapper.findById(id);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.update(article);
    }

    @Override
    public void delete(int id) {
        articleMapper.delete(id);
    }
}
