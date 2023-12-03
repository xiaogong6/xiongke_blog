package com.blog.controller;

import com.api.dto.other.LabelOptionDTO;
import com.api.dto.resource.ResourceDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.ResourceVO;
import com.blog.service.ResourceService;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.DELETE;
import static com.api.constant.OptTypeConstant.SAVE_OR_UPDATE;

/**
 * @author xiongke
 */
@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public ResultVO<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return ResultVO.ok(resourceService.listResources(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public ResultVO<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return ResultVO.ok();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public ResultVO<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public ResultVO<List<LabelOptionDTO>> listResourceOption() {
        return ResultVO.ok(resourceService.listResourceOption());
    }
}
