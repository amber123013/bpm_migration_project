package com.zefu.ssoa.module.bpm.service.task.trigger;

import cn.hutool.core.collection.CollUtil;
import com.zefu.ssoa.framework.common.util.json.JsonUtils;
import com.zefu.ssoa.module.bpm.controller.admin.definition.vo.model.simple.BpmSimpleModelNodeVO.TriggerSetting.NormalFormTriggerSetting;
import com.zefu.ssoa.module.bpm.enums.definition.BpmTriggerTypeEnum;
import com.zefu.ssoa.module.bpm.service.task.BpmProcessInstanceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// TODO @jason：改成 BpmFormUpdateTrigger
/**
 * BPM 更新流程表单触发器
 *
 * @author jason
 */
@Component
@Slf4j
public class BpmUpdateNormalFormTrigger implements BpmTrigger {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTriggerTypeEnum getType() {
        return BpmTriggerTypeEnum.UPDATE_NORMAL_FORM;
    }

    @Override
    public void execute(String processInstanceId, String param) {
        // 1. 解析更新流程表单配置
        NormalFormTriggerSetting setting = JsonUtils.parseObject(param, NormalFormTriggerSetting.class);
        if (setting == null) {
            log.error("[execute][流程({}) 更新流程表单触发器配置为空]", processInstanceId);
            return;
        }
        // 2.更新流程变量
        if (CollUtil.isNotEmpty(setting.getUpdateFormFields())) {
            processInstanceService.updateProcessInstanceVariables(processInstanceId, setting.getUpdateFormFields());
        }
    }

}
