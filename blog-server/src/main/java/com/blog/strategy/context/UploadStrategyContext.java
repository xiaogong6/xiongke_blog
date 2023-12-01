package com.blog.strategy.context;

import com.blog.strategy.UploadStrategy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

import static com.api.enums.UploadModeEnum.getStrategy;


/**
 * @author xiongke
 */
@Service
public class UploadStrategyContext {

    @Value("${upload.mode}")
    private String uploadMode;

    @Resource
    private Map<String, UploadStrategy> uploadStrategyMap;

    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(file, path);
    }

    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(fileName, inputStream, path);
    }

}
