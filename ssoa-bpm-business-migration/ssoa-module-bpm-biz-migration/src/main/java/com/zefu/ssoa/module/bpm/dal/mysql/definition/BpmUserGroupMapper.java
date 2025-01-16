package com.zefu.ssoa.module.bpm.dal.mysql.definition;

import com.zefu.ssoa.framework.common.pojo.PageResult;
import com.zefu.ssoa.framework.mybatis.core.mapper.BaseMapperX;
import com.zefu.ssoa.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.zefu.ssoa.module.bpm.controller.admin.definition.vo.group.BpmUserGroupPageReqVO;
import com.zefu.ssoa.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户组 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmUserGroupMapper extends BaseMapperX<BpmUserGroupDO> {

    default PageResult<BpmUserGroupDO> selectPage(BpmUserGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmUserGroupDO>()
                .likeIfPresent(BpmUserGroupDO::getName, reqVO.getName())
                .eqIfPresent(BpmUserGroupDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BpmUserGroupDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BpmUserGroupDO::getId));
    }

    default List<BpmUserGroupDO> selectListByStatus(Integer status) {
        return selectList(BpmUserGroupDO::getStatus, status);
    }

}
