package com.xukaiqiang.activityDemo.helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


/**
 * @ClassName: HelloWorldProcess
 * @Description: helloWorld流程练习（加载资源文件方式，bpmn,png资源）
 * @author xukaiqiang
 * @date 2017年1月7日 下午1:52:04
 * @modifier
 * @modify-date 2017年1月7日 下午1:52:04
 * @version 1.0
*/
	
public class HelloWorldProcess {
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
	                 .addClasspathResource("diagrams/helloWorld.bpmn")  // 加载资源文件
	                 .addClasspathResource("diagrams/helloWorld.png")   // 加载资源文件
	                 .name("HelloWorld流程")  // 流程名称
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
	        .startProcessInstanceByKey("myFirstProcess"); // 流程定义表的KEY字段值
	    System.out.println("流程实例ID:"+processInstance.getId());
	    System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查看任务
	 */
	@Test
	public void findTask(){
	    // 查询并且返回任务即可
	    List<Task> taskList=processEngine.getTaskService() // 任务相关Service
	            .createTaskQuery()  // 创建任务查询
	            .taskAssignee("java1234_小锋") // 指定某个人
	            .list(); 
	    for(Task task:taskList){
	        System.out.println("任务ID:"+task.getId());
	        System.out.println("任务名称："+task.getName());
	        System.out.println("任务创建时间："+task.getCreateTime());
	        System.out.println("任务委派人："+task.getAssignee());
	        System.out.println("流程实例ID:"+task.getProcessInstanceId());
	    }
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask(){
	    processEngine.getTaskService() // 任务相关Service
	            .complete("2504"); // 指定要完成的任务ID
	}
}
