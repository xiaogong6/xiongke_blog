package com.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.JobLog;
import com.api.dto.base.PageResultDTO;
import com.api.dto.job.JobLogDTO;
import com.api.vo.job.JobLogSearchVO;

import java.util.List;


public interface JobLogService extends IService<JobLog> {

    PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO);

    void deleteJobLogs(List<Integer> ids);

    void cleanJobLogs();

    List<String> listJobLogGroups();

}
