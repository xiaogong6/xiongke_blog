package com.blog.service;


import com.api.dto.aurora.AuroraAdminInfoDTO;
import com.api.dto.aurora.AuroraHomeInfoDTO;
import com.api.dto.other.AboutDTO;
import com.api.dto.other.WebsiteConfigDTO;
import com.api.vo.other.AboutVO;
import com.api.vo.other.WebsiteConfigVO;

public interface AuroraInfoService {

    void report();

    AuroraHomeInfoDTO getAuroraHomeInfo();

    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

    void updateAbout(AboutVO aboutVO);

    AboutDTO getAbout();

}
