package com.blog.service;


import com.api.dto.other.UniqueViewDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.UniqueView;

import java.util.List;

public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();

}
