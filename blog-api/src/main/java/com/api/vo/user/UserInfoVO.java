package com.api.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * @author xiongke
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户信息对象")
public class UserInfoVO {

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    @ApiModelProperty(name = "intro", value = "介绍", dataType = "String")
    private String intro;

    @ApiModelProperty(name = "webSite", value = "个人网站", dataType = "String")
    private String website;

    @ApiModelProperty(name = "email", value = "电子邮件", dataType = "String")
    private String email;

}
