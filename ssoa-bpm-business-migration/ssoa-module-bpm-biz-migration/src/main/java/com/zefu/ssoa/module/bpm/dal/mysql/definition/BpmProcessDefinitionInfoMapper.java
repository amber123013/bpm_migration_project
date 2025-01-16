package com.zefu.ssoa.module.bpm.dal.mysql.definition;

import com.zefu.ssoa.framework.mybatis.core.mapper.BaseMapperX;
import com.zefu.ssoa.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.zefu.ssoa.module.bpm.dal.dataobject.definition.BpmProcessDefinitionInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BpmProcessDefinitionInfoMapper extends BaseMapperX<BpmProcessDefinitionInfoDO> {

    default List<BpmProcessDefinitionInfoDO> selectListByProcessDefinitionIds(Collection<String> processDefinitionIds) {
        return selectList(BpmProcessDefinitionInfoDO::getProcessDefinitionId, processDefinitionIds);
    }

    default BpmProcessDefinitionInfoDO selectByProcessDefinitionId(String processDefinitionId) {
        return selectOne(BpmProcessDefinitionInfoDO::getProcessDefinitionId, processDefinitionId);
    }

    default void updateByModelId(String modelId, BpmProcessDefinitionInfoDO updateObj) {
        update(updateObj,
                new LambdaQueryWrapperX<BpmProcessDefinitionInfoDO>().eq(BpmProcessDefinitionInfoDO::getModelId, modelId));
    }

}