package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {

        // 把文件重命名为UUID加原始文件名的后缀
        String OriginalFilename = file.getOriginalFilename();
        //当 UUID 与 String 进行拼接时，Java 会自动调用 UUID 对象的 toString() 方法，将它转换为字符串，然后再进行拼接操作
        String fileName = UUID.randomUUID() + OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
        // file.transferTo(new File("E:\\PostmanWorkPlace\\Files\\" + fileName));
        // 调用Ali云的工具类上传文件
        String url = AliOssUtil.UploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
