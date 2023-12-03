package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.talk.TalkAdminDTO;
import com.api.dto.talk.TalkDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.TalkVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Talk;

import java.util.List;


public interface TalkService extends IService<Talk> {

    PageResultDTO<TalkDTO> listTalks();

    TalkDTO getTalkById(Integer talkId);

    void saveOrUpdateTalk(TalkVO talkVO);

    void deleteTalks(List<Integer> talkIdList);

    PageResultDTO<TalkAdminDTO> listBackTalks(ConditionVO conditionVO);

    TalkAdminDTO getBackTalkById(Integer talkId);

}
