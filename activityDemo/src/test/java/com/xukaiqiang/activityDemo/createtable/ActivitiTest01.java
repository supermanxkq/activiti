package com.xukaiqiang.activityDemo.createtable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @ClassName: ActivitiTest01
 * @Description: 使用代码,配置文件生成25张表
 * @author xukaiqiang
 * @date 2017年1月7日 下午12:05:46
 * @modifier
 * @modify-date 2017年1月7日 下午12:05:46
 * @version 1.0
 */
public class ActivitiTest01 {

	/**
	 * 生成25张Activiti表
	 */
	@Test
	public void testCreateTable() {
		// 引擎配置
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		pec.setJdbcDriver("com.mysql.jdbc.Driver");
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_activity");
		pec.setJdbcUsername("root");
		pec.setJdbcPassword("root");

		/**
		 * false 不能自动创建表 create-drop 先删除表再创建表 true 自动创建和更新表
		 */
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		// 获取流程引擎对象
		ProcessEngine processEngine = pec.buildProcessEngine();
	}

	/**
	 * 使用xml配置 简化
	 */
	@Test
	public void testCreateTableWithXml() {
		// 引擎配置
		ProcessEngineConfiguration pec = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		// 获取流程引擎对象
		ProcessEngine processEngine = pec.buildProcessEngine();
	}
}
