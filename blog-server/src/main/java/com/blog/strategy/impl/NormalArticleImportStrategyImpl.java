package com.blog.strategy.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.api.vo.article.ArticleVO;
import com.blog.service.ArticleService;
import com.blog.strategy.ArticleImportStrategy;
import com.core.exception.BizException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.api.enums.ArticleStatusEnum.DRAFT;


/**
 * @author xiongke
 */
@Slf4j
@Service("normalArticleImportStrategyImpl")
public class NormalArticleImportStrategyImpl implements ArticleImportStrategy {

    @Resource
    private ArticleService articleService;

    @Override
    public void importArticles(MultipartFile file) {
        String articleTitle = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
        StringBuilder articleContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                articleContent.append((char) reader.read());
            }
        } catch (IOException e) {
            log.error(StrUtil.format("导入文章失败, 堆栈:{}", ExceptionUtil.stacktraceToString(e)));
            throw new BizException("导入文章失败");
        }
        ArticleVO articleVO = ArticleVO.builder()
                .articleTitle(articleTitle)
                .articleContent(articleContent.toString())
                .status(DRAFT.getStatus())
                .build();
        articleService.saveOrUpdateArticle(articleVO);
    }
}
