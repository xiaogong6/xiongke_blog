package com.core.mapper;


import com.api.dto.talk.TalkAdminDTO;
import com.api.dto.talk.TalkDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.modle.entity.Talk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);

    TalkDTO getTalkById(@Param("talkId") Integer talkId);

    List<TalkAdminDTO> listTalksAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    TalkAdminDTO getTalkByIdAdmin(@Param("talkId") Integer talkId);

}
