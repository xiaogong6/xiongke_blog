package com.core.mapper;


import com.api.dto.photo.PhotoAlbumAdminDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.modle.entity.PhotoAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum> {

    List<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO conditionVO);

}
