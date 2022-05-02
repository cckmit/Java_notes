package org.example.question;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author cat
 * @description
 * @date 2022/5/2 下午10:37
 */

@SpringBootTest
public class OssTest {

    @Resource
    OSSClient ossClient;

    @Test
    void testUploadByAlicloudOss() throws FileNotFoundException {
        String bucketName = "passjava-test";
        String localFile = "/Users/cat/一寸照片.jpg";
        String fileKeyName = "coding_java.png";
        InputStream inputStream = new FileInputStream(localFile);
        ossClient.putObject(bucketName, fileKeyName, inputStream);
        ossClient.shutdown();
    }

}
