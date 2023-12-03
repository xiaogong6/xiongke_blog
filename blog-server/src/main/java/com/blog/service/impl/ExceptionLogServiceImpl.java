package com.blog.service.impl;


import com.api.dto.base.PageResultDTO;
import com.api.dto.log.ExceptionLogDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.ExceptionLogService;
import com.core.mapper.ExceptionLogMapper;
import com.core.modle.entity.ExceptionLog;
import com.core.util.BeanCopyUtil;
import com.core.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiongke
 */
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    @Override
    public PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO) {
        Page<ExceptionLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<ExceptionLog> exceptionLogPage = this.page(page, new LambdaQueryWrapper<ExceptionLog>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), ExceptionLog::getOptDesc, conditionVO.getKeywords())
                .orderByDesc(ExceptionLog::getId));
        List<ExceptionLogDTO> list = BeanCopyUtil.copyList(exceptionLogPage.getRecords(), ExceptionLogDTO.class);
        return new PageResultDTO<>(list, (int) exceptionLogPage.getTotal());
    }

}
