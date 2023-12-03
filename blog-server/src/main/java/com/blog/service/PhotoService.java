package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.photo.PhotoAdminDTO;
import com.api.dto.photo.PhotoDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import com.api.vo.photo.PhotoInfoVO;
import com.api.vo.photo.PhotoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Photo;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    PageResultDTO<PhotoAdminDTO> listPhotos(ConditionVO conditionVO);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIds);

    PhotoDTO listPhotosByAlbumId(Integer albumId);

}
