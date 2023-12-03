package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.job.JobDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.job.JobRunVO;
import com.api.vo.job.JobSearchVO;
import com.api.vo.job.JobStatusVO;
import com.api.vo.job.JobVO;
import com.blog.service.JobService;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.constant.OptTypeConstant.*;

/**
 * @author xiongke
 */
@Api(tags = "定时任务模块")
@RestController
public class JobController {

    @Resource
    private JobService jobService;

    @OptLog(optType = SAVE)
    @ApiOperation("添加定时任务")
    @PostMapping("/admin/jobs")
    public ResultVO<?> saveJob(@RequestBody JobVO jobVO) {
        jobService.saveJob(jobVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改定时任务")
    @PutMapping("/admin/jobs")
    public ResultVO<?> updateJob(@RequestBody JobVO jobVO) {
        jobService.updateJob(jobVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation("删除定时任务")
    @DeleteMapping("/admin/jobs")
    public ResultVO<?> deleteJobById(@RequestBody List<Integer> jobIds) {
        jobService.deleteJobs(jobIds);
        return ResultVO.ok();
    }

    @ApiOperation("根据id获取任务")
    @GetMapping("/admin/jobs/{id}")
    public ResultVO<JobDTO> getJobById(@PathVariable("id") Integer jobId) {
        return ResultVO.ok(jobService.getJobById(jobId));
    }

    @ApiOperation("获取任务列表")
    @GetMapping("/admin/jobs")
    public ResultVO<PageResultDTO<JobDTO>> listJobs(JobSearchVO jobSearchVO) {
        return ResultVO.ok(jobService.listJobs(jobSearchVO));
    }

    @ApiOperation("更改任务的状态")
    @PutMapping("/admin/jobs/status")
    public ResultVO<?> updateJobStatus(@RequestBody JobStatusVO jobStatusVO) {
        jobService.updateJobStatus(jobStatusVO);
        return ResultVO.ok();
    }

    @ApiOperation("执行某个任务")
    @PutMapping("/admin/jobs/run")
    public ResultVO<?> runJob(@RequestBody JobRunVO jobRunVO) {
        jobService.runJob(jobRunVO);
        return ResultVO.ok();
    }

    @ApiOperation("获取所有job分组")
    @GetMapping("/admin/jobs/jobGroups")
    public ResultVO<List<String>> listJobGroup() {
        return ResultVO.ok(jobService.listJobGroups());
    }
}
