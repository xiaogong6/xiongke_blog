package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.photo.PhotoAlbumAdminDTO;
import com.api.dto.photo.PhotoAlbumDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.photo.PhotoAlbumVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.PhotoAlbum;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {

    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(ConditionVO condition);

    List<PhotoAlbumDTO> listPhotoAlbumInfosAdmin();

    PhotoAlbumAdminDTO getPhotoAlbumByIdAdmin(Integer albumId);

    void deletePhotoAlbumById(Integer albumId);

    List<PhotoAlbumDTO> listPhotoAlbums();

}
