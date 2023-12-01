package com.blog.mapper;


import com.api.dto.job.JobDTO;
import com.api.vo.job.JobSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modle.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper extends BaseMapper<Job> {

    Integer countJobs(@Param("jobSearchVO") JobSearchVO jobSearchVO);

    List<JobDTO> listJobs(@Param("current") Long current, @Param("size") Long size, @Param("jobSearchVO") JobSearchVO jobSearchVO);

    List<String> listJobGroups();

}
