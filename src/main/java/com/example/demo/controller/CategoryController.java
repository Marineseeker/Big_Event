package com.example.demo.controller;

import com.example.demo.annotation.log;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Result;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public <T> Result<T> add(@RequestBody @Validated(Category.add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @log
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);  
    }

    @log
    @PutMapping
                                            //update需要文章id, 所以使用包含id的Update.class
    public <T> Result<T> update(@RequestBody @Validated(Category.Update.class) Category category){
        // Check if the category with the given id exists
        if (categoryService.findById(category.getId()) == null) {
            return Result.error("Category not found");
        }
        categoryService.update(category);
        return Result.success();
    }
    @log
    @GetMapping("/detail")
    public Result<Category> detail(int id){
        Category category = categoryService.findById(id);
        if (category == null){
            return Result.error("Category not found");
        }
        return Result.success(category);
    }
    @log
    @DeleteMapping
    public <T> Result<T> delete(int id){
        if (categoryService.findById(id) == null){
            return Result.error("Category not found");
        }
        categoryService.delete(id);
        return Result.success();
    }
}
