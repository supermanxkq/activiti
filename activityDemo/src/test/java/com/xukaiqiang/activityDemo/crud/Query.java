package com.xukaiqiang.activityDemo.crud;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class Query {

	/**
	 * 获取默认的流程引擎实例 会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 查询流程定义 返回流程定义集合 ---对应act_re_procdef
	 */
	@Test
	public void list() {
		List<ProcessDefinition> pdList = processEngine.getRepositoryService() // 获取service类
				.createProcessDefinitionQuery() // 创建流程定义查询
				.processDefinitionKey("myFirstProcess") // 通过key查询
				.list(); // 返回一个集合
		for (ProcessDefinition pd : pdList) {
			System.out.println("ID_：" + pd.getId());
			System.out.println("NAME_：" + pd.getName());
			System.out.println("KEY_：" + pd.getKey());
			System.out.println("VERSION_：" + pd.getVersion());
			System.out.println("===================");
		}
	}

	/**
	 * 通过ID查询当个流程定义
	 */
	@Test
	public void getById() {
		ProcessDefinition pd = processEngine.getRepositoryService() // 获取service类
				.createProcessDefinitionQuery() // 创建流程定义查询
				.processDefinitionId("myFirstProcess:1:4") // 通过id查询
				.singleResult(); // 查询返回当个结果
		System.out.println("ID_：" + pd.getId());
		System.out.println("NAME_：" + pd.getName());
		System.out.println("KEY_：" + pd.getKey());
		System.out.println("VERSION_：" + pd.getVersion());
	}

	/**
	 * 删除流程定义,select * from act_re_deployment; #部署对象表
	 */
	@Test
	public void delete() {
		processEngine.getRepositoryService().deleteDeployment("10001"); // 流程部署ID
		System.out.println("delete OK！");
	}
	
	/**
	 * 级联删除 已经在使用的流程实例信息也会被级联删除
	 */
	@Test
	public void deleteCascade(){
	    processEngine.getRepositoryService()
	        .deleteDeployment("10001", true); // 默认是false true就是级联删除
	    System.out.println("delete cascade OK!");
	}
	
	/**
	 * 通过流程部署ID获取流程图图片
	 */
	@Test
	public void getImageById()throws Exception{
	    InputStream inputStream=processEngine.getRepositoryService()
	        .getResourceAsStream("15001", "helloWorld.png"); // 根据流程部署ID和资源名称获取输入流
	    FileUtils.copyInputStreamToFile(inputStream, new File("D:/helloWorld.png"));
	}
	
	/**
	 * 查询最新版本的流程定义
	 */
	@Test
	public void listLastVersion()throws Exception{
	    // 获取流程定义集合，根据Key升序排序
	    List<ProcessDefinition> listAll=processEngine.getRepositoryService() // 获取service类
	            .createProcessDefinitionQuery() // 创建流程定义查询
	            .orderByProcessDefinitionVersion().asc() // 根据流程定义版本升序
	            .list();
	    // 定义有序Map 相同的key 假如添加map的值 后面的值会覆盖前面相同key的值
	    Map<String,ProcessDefinition> map=new LinkedHashMap<String,ProcessDefinition>();
	    // 遍历集合 根据key来覆盖前面的值 来保证最新的Key覆盖前面的所有老的Key的值 
	    for(ProcessDefinition pd:listAll){
	        map.put(pd.getKey(), pd);
	    }
	    List<ProcessDefinition> pdList=new LinkedList<ProcessDefinition>(map.values());
	    for(ProcessDefinition pd:pdList){
	        System.out.println("ID_："+pd.getId());
	        System.out.println("NAME_："+pd.getName());
	        System.out.println("KEY_："+pd.getKey());
	        System.out.println("VERSION_："+pd.getVersion());
	        System.out.println("===================");
	    }
	}
	
	/**
	 * 删除所有Key相同的流程定义
	 * @throws Exception
	 */
	@Test
	public void deleteByKey()throws Exception{
	    List<ProcessDefinition> pdList=processEngine.getRepositoryService()  // 获取service类
	            .createProcessDefinitionQuery() // 创建流程定义查询
	            .processDefinitionKey("myFirstProcess") // 根据Key查询
	            .list();
	    for(ProcessDefinition pd:pdList){  // 遍历集合 获取流程定义的每个部署Id，根据这个id来删除所有流程定义
	        processEngine.getRepositoryService()
	        .deleteDeployment(pd.getDeploymentId(), true); 
	    }
	}
	
	/**
	 * 查询流程状态（正在执行 or 已经执行结束）
	 */
	@Test
	public void processState(){
	    ProcessInstance pi=processEngine.getRuntimeService() // 获取运行时Service
	        .createProcessInstanceQuery() // 创建流程实例查询
	        .processInstanceId("22501") // 用流程实例ID查询Proc_inst_id
	        .singleResult();
	    if(pi!=null){
	        System.out.println("流程正在执行！");
	    }else{
	        System.out.println("流程已经执行结束！");
	    }
	}
	/**
	 * 历史任务查询
	 */
	@Test
	public void historyTaskList(){
	    List<HistoricTaskInstance> list=processEngine.getHistoryService() // 历史任务Service
	            .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
	            .taskAssignee("java1234_小锋") // 指定办理人
	            .finished() // 查询已经完成的任务  
	            .list();
	    for(HistoricTaskInstance hti:list){
	        System.out.println("任务ID:"+hti.getId());
	        System.out.println("流程实例ID:"+hti.getProcessInstanceId());
	        System.out.println("班里人："+hti.getAssignee());
	        System.out.println("创建时间："+hti.getCreateTime());
	        System.out.println("结束时间："+hti.getEndTime());
	        System.out.println("===========================");
	    }
	}
	/**
	 * 查询历史流程实例
	 */
	@Test
	public void getHistoryProcessInstance(){
	    HistoricProcessInstance hpi= processEngine.getHistoryService() // 历史任务Service
	        .createHistoricProcessInstanceQuery() // 创建历史流程实例查询
	        .processInstanceId("2501") // 指定流程实例ID
	        .singleResult();
	    System.out.println("流程实例ID:"+hpi.getId());
	    System.out.println("创建时间："+hpi.getStartTime());
	    System.out.println("结束时间："+hpi.getEndTime());
	}
}
