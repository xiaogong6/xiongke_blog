package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.log.OperationLogDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.OperationLog;

public interface OperationLogService extends IService<OperationLog> {

    PageResultDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
