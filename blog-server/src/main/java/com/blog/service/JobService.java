package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.job.JobDTO;
import com.api.vo.job.JobRunVO;
import com.api.vo.job.JobSearchVO;
import com.api.vo.job.JobStatusVO;
import com.api.vo.job.JobVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Job;

import java.util.List;

public interface JobService extends IService<Job> {

    void saveJob(JobVO jobVO);

    void updateJob(JobVO jobVO);

    void deleteJobs(List<Integer> tagIds);

    JobDTO getJobById(Integer jobId);

    PageResultDTO<JobDTO> listJobs(JobSearchVO jobSearchVO);

    void updateJobStatus(JobStatusVO jobStatusVO);

    void runJob(JobRunVO jobRunVO);

    List<String> listJobGroups();

}
