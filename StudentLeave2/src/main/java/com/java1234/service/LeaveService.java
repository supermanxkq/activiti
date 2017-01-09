package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Leave;

/**
 * 请假单Service
 * @author user
 *
 */
public interface LeaveService {

	/**
	 * 通过id查询请假单实体
	 * @param id
	 * @return
	 */
	public Leave findById(Integer id);
	
	/**
	 * 添加请假单
	 * @param leave
	 * @return
	 */
	public int add(Leave leave);
	
	/**
	 * 修改请假单
	 * @param leave
	 * @return
	 */
	public int update(Leave leave);
	
	/**
	 * 根据条件查询请假单集合
	 * @param map
	 * @return
	 */
	public List<Leave> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}
