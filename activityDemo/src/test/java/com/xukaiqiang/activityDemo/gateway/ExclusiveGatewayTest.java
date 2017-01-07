package com.xukaiqiang.activityDemo.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
 

/**
 * @ClassName: ExclusiveGatewayTest
 * @Description: 排他网关
 * @author xukaiqiang
 * @date 2017年1月7日 下午3:59:52
 * @modifier
 * @modify-date 2017年1月7日 下午3:59:52
 * @version 1.0
*/
	
public class ExclusiveGatewayTest {
 
    /**
     * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
     */
    private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
     
    /**
     * 部署流程定义
     */
    @Test
    public void deploy(){
        Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
                .createDeployment() // 创建部署
                .addClasspathResource("diagrams/StudentLeaveProcess3.bpmn") // 加载资源文件
                .addClasspathResource("diagrams/StudentLeaveProcess3.png") // 加载资源文件
                .name("学生请假流程3") // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:"+deployment.getId()); 
        System.out.println("流程部署Name:"+deployment.getName());
    }
     
    /**
     * 启动流程实例
     */
    @Test
    public void start(){
        ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
            .startProcessInstanceByKey("studentLevaeProcess3"); // 流程定义表的KEY字段值
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId()); 
    }
     
     
    /**
     * 查看任务
     */
    @Test
    public void findTask(){
        List<Task> taskList=processEngine.getTaskService() // 任务相关Service
            .createTaskQuery() // 创建任务查询
            .taskAssignee("王老师") // 指定某个人
            .list();
        for(Task task:taskList){
            System.out.println("任务ID:"+task.getId()); 
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务创建时间:"+task.getCreateTime());
            System.out.println("任务委派人:"+task.getAssignee());
            System.out.println("流程实例ID:"+task.getProcessInstanceId());
        }
    }
     
     
    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        processEngine.getTaskService() // 任务相关Service
            .complete("5004");
    }
     
    @Test
    public void completeTask2(){
 
        Map<String, Object> variables=new HashMap<String,Object>();
        variables.put("days", 2);
        processEngine.getTaskService() // 任务相关Service
            .complete("2504", variables); //完成任务的时候，设置流程变量
    }
     
 
}