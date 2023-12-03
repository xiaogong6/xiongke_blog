package com.blog.service;

import com.api.dto.base.PageResultDTO;
import com.api.dto.tag.TagAdminDTO;
import com.api.dto.tag.TagDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Tag;

import java.util.List;

/**
 * @author xiongke
 */
public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTopTenTags();

    PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO);

    List<TagAdminDTO> listTagsAdminBySearch(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTag(List<Integer> tagIds);

}
