# oss-spring-boot-starter
## 目标：准备提供市面上各类对象存储的适配
### 目前支持：
1. aliyun阿里云oss
2. qcloud腾讯云cos
3. jdcloud京东云oss
### 配置：
oss:  
  &emsp;bucketName: personal  
  &emsp;domain: https://test.personal.com  
  &emsp;aliyun:  
    &emsp;&emsp;#一个项目中只能有一个enable为true  
    &emsp;&emsp;enable: true  
    &emsp;&emsp;endpoint: oss-cn-qingdao.aliyuncs.com  
    &emsp;&emsp;access-key: LTAITIykxcW6Iu  
    &emsp;&emsp;secret-key: cVE45ODy6u6lJ8AH0dy8DiG1Cnv2  
  &emsp;tencent:  
    &emsp;&emsp;#一个项目中只能有一个enable为true  
    &emsp;&emsp;enable: false  
    &emsp;&emsp;access-key: 1234  
    &emsp;&emsp;region: qwe  
    &emsp;&emsp;secret-key: 2345  
  &emsp;jdcloud:  
    &emsp;&emsp;#一个项目中只能有一个enable为true  
    &emsp;&emsp;enable: false  
    &emsp;&emsp;access-key: 1234  
    &emsp;&emsp;endpoint: qwe  
    &emsp;&emsp;secret-key: 2345  
### 使用：
```
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        // 返回 doc_1600658255642.jpg
        return OssFactory.getOss().fileUpload(file,"doc");
    }
    
    @PostMapping("/uploadWithDomain")
    public String uploadWithDomain(MultipartFile file){
        // 返回 https://test.personal.com/doc/doc_1600658255642.jpg
        return OssFactory.getOss().fileUpload(file,"doc", true);
    }

    @PostMapping("/getOssUrl")
    public String getOssUrl(String fileName){
        // 工具类中提供两个外界可用的静态方法
        // 传入 doc_1600658255642.jpg
        // 返回 https://test.personal.com/doc/doc_1600658255642.jpg
        return OssBase.getPathWithDomain(fileName);
    }

    @PostMapping("/download")
    public void download(String fileName,String path){
        // 下载到本地路径
        // 还有一个下载到文件流的方法,敬请探索
        OssFactory.getOss().fileDownload(fileName,path);
    }
```
