package com.blog.service.impl;


import com.api.dto.base.PageResultDTO;
import com.api.dto.photo.PhotoAlbumAdminDTO;
import com.api.dto.photo.PhotoAlbumDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.photo.PhotoAlbumVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.PhotoAlbumService;
import com.core.exception.BizException;
import com.core.mapper.PhotoAlbumMapper;
import com.core.mapper.PhotoMapper;
import com.core.modle.entity.Photo;
import com.core.modle.entity.PhotoAlbum;
import com.core.util.BeanCopyUtil;
import com.core.util.PageUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.api.constant.CommonConstant.FALSE;
import static com.api.constant.CommonConstant.TRUE;
import static com.api.enums.PhotoAlbumStatusEnum.PUBLIC;

@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements PhotoAlbumService {

    @Resource
    private PhotoAlbumMapper photoAlbumMapper;

    @Resource
    private PhotoMapper photoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        PhotoAlbum album = photoAlbumMapper.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getAlbumName, photoAlbumVO.getAlbumName()));
        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVO.getId())) {
            throw new BizException("相册名已存在");
        }
        PhotoAlbum photoAlbum = BeanCopyUtil.copyObject(photoAlbumVO, PhotoAlbum.class);
        this.saveOrUpdate(photoAlbum);
    }

    @Override
    public PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(ConditionVO conditionVO) {
        Integer count = Math.toIntExact(photoAlbumMapper.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), PhotoAlbum::getAlbumName, conditionVO.getKeywords())
                .eq(PhotoAlbum::getIsDelete, FALSE)));
        if (count == 0) {
            return new PageResultDTO<>();
        }
        List<PhotoAlbumAdminDTO> photoAlbumBacks = photoAlbumMapper.listPhotoAlbumsAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(photoAlbumBacks, count);
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumInfosAdmin() {
        List<PhotoAlbum> photoAlbums = photoAlbumMapper.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getIsDelete, FALSE));
        return BeanCopyUtil.copyList(photoAlbums, PhotoAlbumDTO.class);
    }

    @Override
    public PhotoAlbumAdminDTO getPhotoAlbumByIdAdmin(Integer albumId) {
        PhotoAlbum photoAlbum = photoAlbumMapper.selectById(albumId);
        Integer photoCount = Math.toIntExact(photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete, FALSE)));
        PhotoAlbumAdminDTO album = BeanCopyUtil.copyObject(photoAlbum, PhotoAlbumAdminDTO.class);
        album.setPhotoCount(photoCount);
        return album;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePhotoAlbumById(Integer albumId) {
        Integer count = Math.toIntExact(photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)));
        if (count > 0) {
            photoAlbumMapper.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoMapper.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete, TRUE)
                    .eq(Photo::getAlbumId, albumId));
        } else {
            photoAlbumMapper.deleteById(albumId);
        }
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbums() {
        List<PhotoAlbum> photoAlbumList = photoAlbumMapper.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getStatus, PUBLIC.getStatus())
                .eq(PhotoAlbum::getIsDelete, FALSE)
                .orderByDesc(PhotoAlbum::getId));
        return BeanCopyUtil.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }

}
