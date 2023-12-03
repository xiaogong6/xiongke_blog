package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.log.ExceptionLogDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.ExceptionLog;

public interface ExceptionLogService extends IService<ExceptionLog> {

    PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO);

}
