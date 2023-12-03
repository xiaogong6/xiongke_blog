package com.blog.controller;

import com.api.dto.base.PageResultDTO;
import com.api.dto.tag.TagAdminDTO;
import com.api.dto.tag.TagDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.TagVO;
import com.blog.service.TagService;
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
@Api(tags = "标签模块")
@RestController
public class TagController {


    @Resource
    private TagService tagService;

    @ApiOperation("获取所有标签")
    @GetMapping("/tags/all")
    public ResultVO<List<TagDTO>> getAllTags() {
        return ResultVO.ok(tagService.listTags());
    }

    @ApiOperation("获取前十个标签")
    @GetMapping("/tags/topTen")
    public ResultVO<List<TagDTO>> getTopTenTags() {
        return ResultVO.ok(tagService.listTopTenTags());
    }

    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public ResultVO<PageResultDTO<TagAdminDTO>> listTagsAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(tagService.listTagsAdmin(conditionVO));
    }

    @ApiOperation(value = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public ResultVO<List<TagAdminDTO>> listTagsAdminBySearch(ConditionVO condition) {
        return ResultVO.ok(tagService.listTagsAdminBySearch(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/tags")
    public ResultVO<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public ResultVO<?> deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return ResultVO.ok();
    }
}
