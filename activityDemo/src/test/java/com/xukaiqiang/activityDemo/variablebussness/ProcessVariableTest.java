package com.xukaiqiang.activityDemo.variablebussness;

import java.util.Date;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.xukaiqiang.activityDemo.entity.Student;
 

/**
 * @ClassName: ProcessVariableTest
 * @Description: Activiti 之流程变量
 * @author xukaiqiang
 * @date 2017年1月7日 下午2:51:16
 * @modifier
 * @modify-date 2017年1月7日 下午2:51:16
 * @version 1.0
*/
	
public class ProcessVariableTest {
 
    /**
     * 获取默认的流程引擎实例 会自动读取activiti.cfg.xml文件 
     */
    private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
     
    /**
     * 部署流程定义
     */
    @Test
    public void deploy(){
        // 获取部署对象
        Deployment deployment=processEngine.getRepositoryService() // 部署Service
                     .createDeployment()  // 创建部署
                     .addClasspathResource("diagrams/StudentLeave.bpmn")  // 加载资源文件
                     .addClasspathResource("diagrams/StudentLeave.png")   // 加载资源文件
                     .name("学生请假流程")  // 流程名称
                     .deploy(); // 部署
        System.out.println("流程部署ID:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
    }
    
    /**
     * 启动流程实例
     */
    @Test
    public void start(){
        // 启动并获取流程实例
        ProcessInstance processInstance=processEngine.getRuntimeService() // 运行时流程实例Service
            .startProcessInstanceByKey("studentLeaveProcess"); // 流程定义表的KEY字段值
        System.out.println("流程实例ID:"+processInstance.getId());
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
    }
    
    
    /**
     * 设置流程变量以及值
     */
    @Test
    public void setVariablesValues(){
        TaskService taskService=processEngine.getTaskService(); // 任务Service
        String taskId="27504"; // 任务id
        taskService.setVariableLocal(taskId, "days", 2); // 存Integer类型
        taskService.setVariable(taskId, "date", new Date()); // 存日期类型
        taskService.setVariable(taskId, "reason", "发烧"); // 存字符串
        Student student=new Student();
        student.setId(1);
        student.setName("张三");
        taskService.setVariable(taskId, "student", student);  // 存序列化对象
    }
    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        processEngine.getTaskService() // 任务相关Service
                .complete("32502"); // 指定要完成的任务ID
    }
    
    /**
     * 获取流程变量以及值   正在执行的流程变量表
     */
    @Test
    public void getVariablesValue(){
        TaskService taskService=processEngine.getTaskService(); // 任务Service
        String taskId="32502"; // 任务id
        Integer days=(Integer) taskService.getVariable(taskId, "days");
        Date date=(Date) taskService.getVariable(taskId, "date");
        String reason=(String) taskService.getVariable(taskId, "reason");
        Student student=(Student) taskService.getVariable(taskId, "student");
        System.out.println("请假天数："+days);
        System.out.println("请假日期："+date);
        System.out.println("请假原因："+reason);
        System.out.println("请假对象："+student.getId()+","+student.getName());
    }
}