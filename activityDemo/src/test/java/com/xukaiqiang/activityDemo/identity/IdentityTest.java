package com.xukaiqiang.activityDemo.identity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.Test;

/**
 * @ClassName: IdentityTest
 * @Description:
 * Activiti之内置用户组设计表以及IdentityServiceActiviti给我们提供了一套内置的用户和组设计表； 用户和组(或者叫做角色)，多对多关联
 *                                                                            ，
 *                                                                            通过关联表实现；
 * @author xukaiqiang
 * @date 2017年1月7日 下午5:38:37
 * @modifier
 * @modify-date 2017年1月7日 下午5:38:37
 * @version 1.0
 */

public class IdentityTest {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 添加用户测试
	 */
	@Test
	public void testSaveUser() {
		IdentityService identityService = processEngine.getIdentityService();
		User user1 = new UserEntity(); // 实例化用户实体
		user1.setId("lisi");
		user1.setEmail("234@qq.com");
		user1.setPassword("123456");
		identityService.saveUser(user1); // 添加用户
		User user2 = new UserEntity(); // 实例化用户实体
		user2.setId("zhangsan");
		user2.setEmail("234@qq.com");
		user2.setPassword("123456");
		identityService.saveUser(user2); // 添加用户
	}

	/**
	 * 测试删除用户
	 */
	@Test
	public void testDeleteUser() {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteUser("lisi");
	}

	/**
	 * 测试添加组（角色）
	 */
	@Test
	public void testSaveGroup() {
		IdentityService identityService = processEngine.getIdentityService();
		Group group = new GroupEntity(); // 实例化组实体
		group.setId("test");
		identityService.saveGroup(group);
		Group group1 = new GroupEntity(); // 实例化组实体
		group1.setId("dev");
		identityService.saveGroup(group1);
	}

	/**
	 * 测试删除组
	 */
	@Test
	public void testDeleteGroup() {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteGroup("test");
	}

	/**
	 * 测试添加用户和组关联关系
	 */
	@Test
	public void testSaveMembership() {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.createMembership("lisi", "dev");
		identityService.createMembership("zhangsan", "dev");
	}

	/**
	 * 测试删除用户和组关联关系
	 */
	@Test
	public void testDeleteMembership() {
		IdentityService identityService = processEngine.getIdentityService();
		identityService.deleteMembership("lisi", "test");
	}

}
