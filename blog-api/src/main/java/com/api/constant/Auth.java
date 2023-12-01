package com.api.constant;


/**
 * @author FrozenWatermelon
 * @date 2020/12/8
 */
public interface Auth {

    String CHECK_TOKEN_URI = FeignAuthConstant.FEIGN_INSIDE_URL_PREFIX + "/token/checkToken";

    String CHECK_RBAC_URI = FeignAuthConstant.FEIGN_INSIDE_URL_PREFIX + "/insider/permission/checkPermission";
}
