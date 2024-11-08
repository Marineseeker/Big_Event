package com.example.demo.mapper;

import com.example.demo.pojo.Category;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);

    @Select("select * from category where create_user = #{userId}")
    List<Category> list(int userId);

    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time  =#{updateTime} where id = #{id}")
    void update(Category category);

    @Select("select * from category where id = #{id}")
    Category findById(int id);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(int id);
}
