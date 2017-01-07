package com.xukaiqiang.activityDemo.crud;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;

public class HistoryActivitiQuery {
	/**
     * 获取默认的流程引擎实例 会自动读取activiti.cfg.xml文件 
     */
    private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	/**
     * 历史活动查询
     */
    @Test
    public void historyActInstanceList(){
        List<HistoricActivityInstance> list=processEngine.getHistoryService() // 历史任务Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId("27501") // 指定流程实例id
                .finished() // 查询已经完成的任务  
                .list();
        for(HistoricActivityInstance hai:list){
            System.out.println("任务ID:"+hai.getId());
            System.out.println("流程实例ID:"+hai.getProcessInstanceId());
            System.out.println("活动名称："+hai.getActivityName());
            System.out.println("办理人："+hai.getAssignee());
            System.out.println("开始时间："+hai.getStartTime());
            System.out.println("结束时间："+hai.getEndTime());
            System.out.println("===========================");
        }
    }
}
