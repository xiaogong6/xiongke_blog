package com.core.mapper;


import com.api.dto.other.UniqueViewDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.modle.entity.UniqueView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UniqueViewMapper extends BaseMapper<UniqueView> {

    List<UniqueViewDTO> listUniqueViews(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
