package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    // Find user by username
    @Select("Select * from user where username = #{username}")
    User findByUsername(String username);

    // Add user
    @Insert("Insert into user(username, password, create_time, update_time)"
            +"values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    @Update("Update user set nickname = #{nickname}, email = #{email}, update_time = #{updateTime} where id = #{id}")
    void update(User user);

    @Update("Update user set user_pic = #{avatarUrl}, update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl, int id);

    @Update("Update user set password = #{newPwd}, update_time = now() where id = #{id}")
    void updatePwd(String newPwd, int id);
}
