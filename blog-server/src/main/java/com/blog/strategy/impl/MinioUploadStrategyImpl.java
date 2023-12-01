package com.blog.strategy.impl;


import com.core.config.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author xiongke
 */
@Service("minioUploadStrategyImpl")
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Resource
    private MinioProperties minioProperties;

    @Override
    public Boolean exists(String filePath) {
        boolean exist = true;
        try {
            getMinioClient()
                    .statObject(StatObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @SneakyThrows
    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        getMinioClient().putObject(
                PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(path + fileName).stream(
                                inputStream, inputStream.available(), -1)
                        .build());
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getUrl() + filePath;
    }

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
