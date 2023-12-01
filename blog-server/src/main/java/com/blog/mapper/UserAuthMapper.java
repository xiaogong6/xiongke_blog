package com.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modle.entity.UserAuth;
import com.api.dto.user.UserAdminDTO;
import com.api.vo.other.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    List<UserAdminDTO> listUsers(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    Integer countUser(@Param("conditionVO") ConditionVO conditionVO);

}
