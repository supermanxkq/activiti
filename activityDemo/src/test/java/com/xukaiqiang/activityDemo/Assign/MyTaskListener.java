package com.xukaiqiang.activityDemo.Assign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @ClassName: TaskListener
 * @Description: 分配方式三 TaskListener监听实现
 * @author xukaiqiang
 * @date 2017年1月7日 下午4:43:40
 * @modifier
 * @modify-date 2017年1月7日 下午4:43:40
 * @version 1.0
 */

public class MyTaskListener implements TaskListener {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
		
	private static final long serialVersionUID = -5697967319761238979L;

	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee("李四"); // 指定办理人
	}

}
