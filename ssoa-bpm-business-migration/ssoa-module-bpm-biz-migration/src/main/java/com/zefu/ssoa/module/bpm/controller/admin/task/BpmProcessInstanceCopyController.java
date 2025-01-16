package com.zefu.ssoa.module.bpm.controller.admin.task;

import cn.hutool.core.collection.CollUtil;
import com.zefu.ssoa.framework.common.pojo.CommonResult;
import com.zefu.ssoa.framework.common.pojo.PageResult;
import com.zefu.ssoa.framework.common.util.collection.MapUtils;
import com.zefu.ssoa.framework.common.util.date.DateUtils;
import com.zefu.ssoa.framework.common.util.object.BeanUtils;
import com.zefu.ssoa.module.bpm.controller.admin.base.user.UserSimpleBaseVO;
import com.zefu.ssoa.module.bpm.controller.admin.task.vo.cc.BpmProcessInstanceCopyRespVO;
import com.zefu.ssoa.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCopyPageReqVO;
import com.zefu.ssoa.module.bpm.dal.dataobject.task.BpmProcessInstanceCopyDO;
import com.zefu.ssoa.module.bpm.service.task.BpmProcessInstanceCopyService;
import com.zefu.ssoa.module.bpm.service.task.BpmProcessInstanceService;
import com.zefu.ssoa.module.system.api.user.AdminUserApi;
import com.zefu.ssoa.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Stream;

import static com.zefu.ssoa.framework.common.pojo.CommonResult.success;
import static com.zefu.ssoa.framework.common.util.collection.CollectionUtils.*;
import static com.zefu.ssoa.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 流程实例抄送")
@RestController
@RequestMapping("/bpm/process-instance/copy")
@Validated
public class BpmProcessInstanceCopyController {

    @Resource
    private BpmProcessInstanceCopyService processInstanceCopyService;
    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Resource
    private AdminUserApi adminUserApi;

    @GetMapping("/page")
    @Operation(summary = "获得抄送流程分页列表")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance-cc:query')")
    public CommonResult<PageResult<BpmProcessInstanceCopyRespVO>> getProcessInstanceCopyPage(
            @Valid BpmProcessInstanceCopyPageReqVO pageReqVO) {
        PageResult<BpmProcessInstanceCopyDO> pageResult = processInstanceCopyService.getProcessInstanceCopyPage(
                getLoginUserId(), pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }

        // 拼接返回
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getProcessInstanceId));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(pageResult.getList(),
                copy -> Stream.of(copy.getStartUserId(), Long.parseLong(copy.getCreator()))));
        return success(convertPage(pageResult, copy -> {
            BpmProcessInstanceCopyRespVO copyVO = BeanUtils.toBean(copy, BpmProcessInstanceCopyRespVO.class);
            MapUtils.findAndThen(userMap, Long.valueOf(copy.getCreator()),
                    user -> copyVO.setStartUser(BeanUtils.toBean(user, UserSimpleBaseVO.class)));
            MapUtils.findAndThen(userMap, copy.getStartUserId(),
                    user -> copyVO.setCreateUser(BeanUtils.toBean(user, UserSimpleBaseVO.class)));
            MapUtils.findAndThen(processInstanceMap, copyVO.getProcessInstanceId(),
                    processInstance -> copyVO.setProcessInstanceStartTime(DateUtils.of(processInstance.getStartTime())));
            return copyVO;
        }));
    }

}