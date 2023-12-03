package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.log.ExceptionLogDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.blog.service.ExceptionLogService;
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
@Api(tags = "异常日志模块")
@RestController
public class ExceptionLogController {

    @Resource
    private ExceptionLogService exceptionLogService;

    @ApiOperation("获取异常日志")
    @GetMapping("/admin/exception/logs")
    public ResultVO<PageResultDTO<ExceptionLogDTO>> listExceptionLogs(ConditionVO conditionVO) {
        return ResultVO.ok(exceptionLogService.listExceptionLogs(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除异常日志")
    @DeleteMapping("/admin/exception/logs")
    public ResultVO<?> deleteExceptionLogs(@RequestBody List<Integer> exceptionLogIds) {
        exceptionLogService.removeByIds(exceptionLogIds);
        return ResultVO.ok();
    }

}
