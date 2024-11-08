package com.example.demo.pojo;

import com.example.demo.annotation.StateConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty(message = "文章标题不能为空", groups = Update.class)
    @Pattern(
            regexp = "^[\\u4e00-龥a-zA-Z0-9《》【】「」『』（）()\\s]{2,50}$",
            message = "标题格式不正确：只能包含中文、英文、数字、常用标点符号，长度在2-50个字符之间"
    )
    @Length(min = 2, max = 50, message = "标题长度必须在2-50个字符之间")
    private String title;//文章标题
    @NotEmpty(groups = Update.class)
    private String content;//文章内容
    @NotEmpty(groups = Update.class)
    @URL
    private String coverImg;//封面图像

    @NotEmpty(message = "发布状态不能为空", groups = Update.class)
    @StateConstraint // 使用自定义State注解
    private String state;//发布状态 已发布|草稿
    @NotNull(groups = Update.class)
    private Integer categoryId;//文章分类id

    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

    public interface Update{}
}
