package com.example.demo.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.InputStream;

public class AliOssUtil {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    private static final String accessKeyId = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID");
    private static final String accessKeySecret = System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET");
    // 填写Bucket名称，例如examplebucket。
    private static final String bucketName = "marinedreams";
    // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
    // String region = "cn-hangzhou";


    public static String UploadFile(String objectName, InputStream in) {
        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V2);
//        OSS ossClient = OSSClientBuilder.create()
//                .endpoint(endpoint)
//                .credentialsProvider(credentialsProvider)
//                .clientConfiguration(clientBuilderConfiguration)
//                .region(region)
//                .build();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String url  = "";
        try {
//            // 填写字符串。
//            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 拼接URL
            url = "https://" + bucketName + "." + endpoint.substring(endpoint.indexOf("//") + 2) + "/" + objectName;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
