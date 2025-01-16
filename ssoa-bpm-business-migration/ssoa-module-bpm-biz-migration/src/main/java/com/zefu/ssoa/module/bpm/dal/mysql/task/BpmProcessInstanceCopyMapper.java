package com.zefu.ssoa.module.bpm.dal.mysql.task;

import com.zefu.ssoa.framework.common.pojo.PageResult;
import com.zefu.ssoa.framework.mybatis.core.mapper.BaseMapperX;
import com.zefu.ssoa.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.zefu.ssoa.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCopyPageReqVO;
import com.zefu.ssoa.module.bpm.dal.dataobject.task.BpmProcessInstanceCopyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BpmProcessInstanceCopyMapper extends BaseMapperX<BpmProcessInstanceCopyDO> {

    default PageResult<BpmProcessInstanceCopyDO> selectPage(Long loginUserId, BpmProcessInstanceCopyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmProcessInstanceCopyDO>()
                .eqIfPresent(BpmProcessInstanceCopyDO::getUserId, loginUserId)
                .likeIfPresent(BpmProcessInstanceCopyDO::getProcessInstanceName, reqVO.getProcessInstanceName())
                .betweenIfPresent(BpmProcessInstanceCopyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BpmProcessInstanceCopyDO::getId));
    }

    default List<BpmProcessInstanceCopyDO> selectListByProcessInstanceIdAndActivityId(String processInstanceId, String activityId) {
        return selectList(BpmProcessInstanceCopyDO::getProcessInstanceId, processInstanceId,
                BpmProcessInstanceCopyDO::getActivityId, activityId);
    }

}
