package com.blog.service.impl;


import com.api.dto.base.PageResultDTO;
import com.api.dto.job.JobLogDTO;
import com.api.vo.job.JobLogSearchVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.JobLogService;
import com.core.mapper.JobLogMapper;
import com.core.modle.entity.JobLog;
import com.core.util.BeanCopyUtil;
import com.core.util.PageUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Resource
    private JobLogMapper jobLogMapper;

    @SneakyThrows
    @Override
    public PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<JobLog>()
                .orderByDesc(JobLog::getCreateTime)
                .eq(Objects.nonNull(jobLogSearchVO.getJobId()), JobLog::getJobId, jobLogSearchVO.getJobId())
                .like(StringUtils.isNotBlank(jobLogSearchVO.getJobName()), JobLog::getJobName, jobLogSearchVO.getJobName())
                .like(StringUtils.isNotBlank(jobLogSearchVO.getJobGroup()), JobLog::getJobGroup, jobLogSearchVO.getJobGroup())
                .eq(Objects.nonNull(jobLogSearchVO.getStatus()), JobLog::getStatus, jobLogSearchVO.getStatus())
                .between(Objects.nonNull(jobLogSearchVO.getStartTime()) && Objects.nonNull(jobLogSearchVO.getEndTime()),
                        JobLog::getCreateTime,
                        jobLogSearchVO.getStartTime(),
                        jobLogSearchVO.getEndTime());
        Page<JobLog> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<JobLog> jobLogPage = jobLogMapper.selectPage(page, queryWrapper);
        List<JobLogDTO> jobLogDTOs = BeanCopyUtil.copyList(jobLogPage.getRecords(), JobLogDTO.class);
        return new PageResultDTO<>(jobLogDTOs, (int) jobLogPage.getTotal());
    }

    @Override
    public void deleteJobLogs(List<Integer> ids) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<JobLog>().in(JobLog::getId, ids);
        jobLogMapper.delete(queryWrapper);
    }

    @Override
    public void cleanJobLogs() {
        jobLogMapper.delete(null);
    }

    @Override
    public List<String> listJobLogGroups() {
        return jobLogMapper.listJobLogGroups();
    }

}
