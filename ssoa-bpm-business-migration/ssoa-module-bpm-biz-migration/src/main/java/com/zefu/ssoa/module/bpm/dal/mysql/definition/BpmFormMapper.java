package com.zefu.ssoa.module.bpm.dal.mysql.definition;


import com.zefu.ssoa.framework.common.pojo.PageResult;
import com.zefu.ssoa.framework.mybatis.core.mapper.BaseMapperX;
import com.zefu.ssoa.framework.mybatis.core.query.QueryWrapperX;
import com.zefu.ssoa.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import com.zefu.ssoa.module.bpm.dal.dataobject.definition.BpmFormDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态表单 Mapper
 *
 * @author 风里雾里
 */
@Mapper
public interface BpmFormMapper extends BaseMapperX<BpmFormDO> {

    default PageResult<BpmFormDO> selectPage(BpmFormPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<BpmFormDO>()
                .likeIfPresent("name", reqVO.getName())
                .orderByDesc("id"));
    }

}
