package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.friendlink.FriendLinkAdminDTO;
import com.api.dto.friendlink.FriendLinkDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.FriendLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.FriendLink;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks();

    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdmin(ConditionVO conditionVO);

    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
