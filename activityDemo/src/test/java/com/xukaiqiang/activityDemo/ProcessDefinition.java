package com.xukaiqiang.activityDemo;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;


/**
 * @ClassName: ProcessDefinition
 * @Description: 
 * @author xukaiqiang
 * @date 2017年1月7日 下午4:18:02
 * @modifier
 * @modify-date 2017年1月7日 下午4:18:02
 * @version 1.0
*/
public class ProcessDefinition {
	
	/**
	 * 获取默认的流程引擎实例 会自动读取activiti.cfg.xml文件 
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署流程定义使用zip方式
	 */
	@Test
	public void deployWithZip(){
	    InputStream inputStream=this.getClass()  // 获取当前class对象
	                        .getClassLoader()   // 获取类加载器
	                        .getResourceAsStream("diagrams/helloWorld.zip"); // 获取指定文件资源流
	    ZipInputStream zipInputStream=new ZipInputStream(inputStream); // 实例化zip输入流对象
	    // 获取部署对象
	    Deployment deployment=processEngine.getRepositoryService() // 部署Service
	                 .createDeployment()  // 创建部署
	                 .name("HelloWorld流程2")  // 流程名称
	                 .addZipInputStream(zipInputStream)  // 添加zip是输入流
	                 .deploy(); // 部署
	    System.out.println("流程部署ID:"+deployment.getId());
	    System.out.println("流程部署Name:"+deployment.getName());
	}
}
