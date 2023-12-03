package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.photo.PhotoAdminDTO;
import com.api.dto.photo.PhotoDTO;
import com.api.enums.FilePathEnum;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import com.api.vo.photo.PhotoInfoVO;
import com.api.vo.photo.PhotoVO;
import com.blog.service.PhotoService;
import com.blog.strategy.context.UploadStrategyContext;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.*;

/**
 * @author xiongke
 */
@Api(tags = "照片模块")
@RestController
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @OptLog(optType = UPLOAD)
    @ApiOperation(value = "上传照片")
    @ApiImplicitParam(name = "file", value = "照片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/photos/upload")
    public ResultVO<String> savePhotoAlbumCover(MultipartFile file) {
        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    @ApiOperation(value = "根据相册id获取照片列表")
    @GetMapping("/admin/photos")
    public ResultVO<PageResultDTO<PhotoAdminDTO>> listPhotos(ConditionVO conditionVO) {
        return ResultVO.ok(photoService.listPhotos(conditionVO));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新照片信息")
    @PutMapping("/admin/photos")
    public ResultVO<?> updatePhoto(@Valid @RequestBody PhotoInfoVO photoInfoVO) {
        photoService.updatePhoto(photoInfoVO);
        return ResultVO.ok();
    }

    @OptLog(optType = SAVE)
    @ApiOperation(value = "保存照片")
    @PostMapping("/admin/photos")
    public ResultVO<?> savePhotos(@Valid @RequestBody PhotoVO photoVO) {
        photoService.savePhotos(photoVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "移动照片相册")
    @PutMapping("/admin/photos/album")
    public ResultVO<?> updatePhotosAlbum(@Valid @RequestBody PhotoVO photoVO) {
        photoService.updatePhotosAlbum(photoVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新照片删除状态")
    @PutMapping("/admin/photos/delete")
    public ResultVO<?> updatePhotoDelete(@Valid @RequestBody DeleteVO deleteVO) {
        photoService.updatePhotoDelete(deleteVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除照片")
    @DeleteMapping("/admin/photos")
    public ResultVO<?> deletePhotos(@RequestBody List<Integer> photoIds) {
        photoService.deletePhotos(photoIds);
        return ResultVO.ok();
    }

    @ApiOperation(value = "根据相册id查看照片列表")
    @GetMapping("/albums/{albumId}/photos")
    public ResultVO<PhotoDTO> listPhotosByAlbumId(@PathVariable("albumId") Integer albumId) {
        return ResultVO.ok(photoService.listPhotosByAlbumId(albumId));
    }

}
