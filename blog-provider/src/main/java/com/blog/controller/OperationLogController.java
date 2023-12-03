package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.log.OperationLogDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.blog.service.OperationLogService;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.api.constant.OptTypeConstant.DELETE;

/**
 * @author xiongke
 */
@Api(tags = "操作日志模块")
@RestController
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public ResultVO<PageResultDTO<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return ResultVO.ok(operationLogService.listOperationLogs(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/admin/operation/logs")
    public ResultVO<?> deleteOperationLogs(@RequestBody List<Integer> operationLogIds) {
        operationLogService.removeByIds(operationLogIds);
        return ResultVO.ok();
    }

}
