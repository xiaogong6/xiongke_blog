package com.blog.controller;


import com.api.dto.aurora.AuroraAdminInfoDTO;
import com.api.dto.aurora.AuroraHomeInfoDTO;
import com.api.dto.other.AboutDTO;
import com.api.dto.other.WebsiteConfigDTO;
import com.api.enums.FilePathEnum;
import com.api.vo.base.ResultVO;
import com.api.vo.other.AboutVO;
import com.api.vo.other.WebsiteConfigVO;
import com.blog.service.AuroraInfoService;
import com.blog.strategy.context.UploadStrategyContext;
import com.core.annotation.AccessLimit;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.api.constant.OptTypeConstant.UPDATE;
import static com.api.constant.OptTypeConstant.UPLOAD;


/**
 * @author xiongke
 */
@Api(tags = "aurora信息")
@RestController
public class AuroraInfoController {

    @Resource
    private AuroraInfoService auroraInfoService;

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation(value = "上报访客信息")
    @PostMapping("/report")
    public ResultVO<?> report() {
        auroraInfoService.report();
        return ResultVO.ok();
    }

    @ApiOperation(value = "获取系统信息")
    @GetMapping("/")
    @AccessLimit(seconds = 1, maxCount = 10)
    public ResultVO<AuroraHomeInfoDTO> getBlogHomeInfo() {
        return ResultVO.ok(auroraInfoService.getAuroraHomeInfo());
    }

    @ApiOperation(value = "获取系统后台信息")
    @GetMapping("/admin")
    public ResultVO<AuroraAdminInfoDTO> getBlogBackInfo() {
        return ResultVO.ok(auroraInfoService.getAuroraAdminInfo());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/config")
    public ResultVO<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        auroraInfoService.updateWebsiteConfig(websiteConfigVO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/config")
    public ResultVO<WebsiteConfigDTO> getWebsiteConfig() {
        return ResultVO.ok(auroraInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public ResultVO<AboutDTO> getAbout() {
        return ResultVO.ok(auroraInfoService.getAbout());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public ResultVO<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        auroraInfoService.updateAbout(aboutVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation(value = "上传博客配置图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/config/images")
    public ResultVO<String> savePhotoAlbumCover(MultipartFile file) {
        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

}
