package com.api.feign;

import com.api.dto.user.UserDetailsDTO;
import com.api.response.ServerResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "TokenClient", path = "/api/token")
public interface TokenClient {

    @GetMapping(value = "/getUserDetailDTO")
    ServerResponseEntity<UserDetailsDTO> getUserDetailDTO(@RequestParam("request") HttpServletRequest request);

    @PostMapping(value = "/renewToken")
    ServerResponseEntity<?> renewToken(@RequestBody UserDetailsDTO userDetailsDTO);

    @PostMapping(value = "/createToken")
    ServerResponseEntity<String> createToken(@RequestBody UserDetailsDTO userDetailsDTO);

}
