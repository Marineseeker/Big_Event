package com.example.demo;

import com.aliyun.vod.upload.impl.UploadImageImpl;
import com.aliyun.vod.upload.req.UploadImageRequest;
import com.aliyun.vod.upload.resp.UploadImageResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UploadImageDemo {
    // 账号AK信息请填写（必选）
    // 阿里云账号AccessKey拥有所有API的访问权限，建议您使用RAM用户进行API访问或日常运维。
    // 强烈建议不要把AccessKey ID和AccessKey Secret保存到工程代码里，否则可能导致AccessKey泄露，威胁您账号下所有资源的安全。
    // 本示例通过从环境变量中读取AccessKey，来实现API访问的身份验证。运行代码示例前，请配置环境变量ALIBABA_CLOUD_ACCESS_KEY_ID和ALIBABA_CLOUD_ACCESS_KEY_SECRET。
    private static final String accessKeyId = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
    private static final String accessKeySecret = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");

    public static void main(String[] args) {
        // 图片上传
        // 1.图片上传-本地文件上传
        testUploadImageLocalFile(accessKeyId, accessKeySecret);
        // 2.图片上传-流式上传（文件流和网络流）
        //testUploadImageStream(accessKeyId, accessKeySecret);
    }
    /**
     * 图片上传接口，本地文件上传示例
     *
     * @param accessKeyId
     * @param accessKeySecret
     */

    private static void testUploadImageLocalFile(String accessKeyId, String accessKeySecret) {
        /* 图片类型（必选）取值范围：default（默认），cover（封面），watermark（水印）*/
        String imageType = "cover";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        request.setImageType("cover");
        /* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
        //request.setImageExt("png");
        /* 图片标题（可选）长度不超过128个字节，UTF8编码 */
        //request.setTitle("图片标题");
        /* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
        //request.setTags("标签1,标签2");
        /* 存储区域（可选）*/
        //request.setStorageLocation("out-4f3952f78c0211e8b30200****.oss-cn-shanghai.aliyuncs.com");
        /* 流式上传时，InputStream为必选，fileName为源文件名称，如：文件名称.png（可选）*/
        String fileName = "E:\\PostmanWorkPlace\\img\\23.png";
        request.setFileName(fileName);
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(false);
        /* 设置自定义上传进度回调 （必须继承 VoDProgressListener）*/
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-100****");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");

        /* 配置代理访问（可选） */
        //OSSConfig ossConfig = new OSSConfig();
        /* <必填>设置代理服务器主机地址 */
        //ossConfig.setProxyHost("<yourProxyHost>");
        /* <必填>设置代理服务器端口 */
        //ossConfig.setProxyPort(-1);
        /* 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP */
        //ossConfig.setProtocol("HTTP");
        /* 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java */
        //ossConfig.setUserAgent("<yourUserAgent>");
        /* 设置代理服务器验证的用户名，https协议时需要填 */
        //ossConfig.setProxyUsername("<yourProxyUserName>");
        /* 设置代理服务器验证的密码，https协议时需要填 */
        //ossConfig.setProxyPassword("<yourProxyPassword>");
        //request.setOssConfig(ossConfig);

        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("ImageId=" + response.getImageId() + "\n");
            System.out.print("ImageURL=" + response.getImageURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    /**
     * 图片上传接口，流式上传示例（支持文件流和网络流）
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadImageStream(String accessKeyId, String accessKeySecret) {
        /* 图片类型（必选）取值范围：default（默认），cover（封面），watermark（水印）*/
        String imageType = "cover";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        /* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
        //request.setImageExt("png");
        /* 图片标题（可选）长度不超过128个字节，UTF8编码 */
        //request.setTitle("图片标题");
        /* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
        //request.setTags("标签1,标签2");
        /* 存储区域（可选）*/
        //request.setStorageLocation("out-4f3952f78c0211e8b30200****.oss-cn-shanghai.aliyuncs.com");
        /* 流式上传时，InputStream为必选，fileName为源文件名称，如：文件名称.png（可选）*/
        //request.setFileName("测试文件名称.png");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 （必须继承 VoDProgressListener） */
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");

        /* 配置代理访问（可选） */
        //OSSConfig ossConfig = new OSSConfig();
        /* <必填>设置代理服务器主机地址 */
        //ossConfig.setProxyHost("<yourProxyHost>");
        /* <必填>设置代理服务器端口 */
        //ossConfig.setProxyPort(-1);
        /* 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP */
        //ossConfig.setProtocol("HTTP");
        /* 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java */
        //ossConfig.setUserAgent("<yourUserAgent>");
        /* 设置代理服务器验证的用户名，https协议时需要填 */
        //ossConfig.setProxyUsername("<yourProxyUserName>");
        /* 设置代理服务器验证的密码，https协议时需要填 */
        //ossConfig.setProxyPassword("<yourProxyPassword>");
        //request.setOssConfig(ossConfig);

        // 1.文件流上传
        // InputStream fileStream = getFileStream(request.getFileName());
        // if (fileStream != null) {
        //     request.setInputStream(fileStream);
        // }
        // 2.网络流上传
        String url = "http://test.aliyun.com/image/default/test.png";
        InputStream urlStream = getUrlStream(url);
        if (urlStream != null) {
            request.setInputStream(urlStream);
        }
        // 开始上传图片
        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("ImageId=" + response.getImageId() + "\n");
            System.out.print("ImageURL=" + response.getImageURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    private static InputStream getFileStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static InputStream getUrlStream(String url) {
        try {
            return new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}