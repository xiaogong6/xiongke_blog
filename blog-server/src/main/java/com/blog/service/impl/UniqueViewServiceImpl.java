package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.api.dto.other.UniqueViewDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.UniqueViewService;
import com.core.mapper.UniqueViewMapper;
import com.core.modle.entity.UniqueView;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {

    @Resource
    private UniqueViewMapper uniqueViewMapper;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewMapper.listUniqueViews(startTime, endTime);
    }

}
