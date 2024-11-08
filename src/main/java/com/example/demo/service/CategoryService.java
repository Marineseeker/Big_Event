package com.example.demo.service;

import java.util.List;

import com.example.demo.pojo.Category;

public interface CategoryService{
    
    void add(Category category);

    List<Category> list();

    void update(Category category);

    Category findById(int id);

    void delete(int id);
}
