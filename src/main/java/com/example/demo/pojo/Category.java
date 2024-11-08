package com.example.demo.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID

    @NotEmpty(message = "分类名称不能为空", groups = {Update.class, add.class})
    private String categoryName;//分类名称

    @NotEmpty(message = "分类别名不能为空", groups = {Update.class, add.class})
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    
    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //Update中的校验项包括id, Default的校验项不包括id
    public interface Update{}
    public interface add{}

}
