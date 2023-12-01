package com.core.convert;

import com.core.modle.bo.JobLogBO;
import com.core.modle.entity.JobLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Mapper
public interface CoreConvert {

    CoreConvert INSTANCE = Mappers.getMapper(CoreConvert.class);

    JobLog converToJobLog(JobLogBO bo);
}
