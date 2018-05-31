package com.qingguohd.red.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingguohd.red.config.RedisUtils;
import com.qingguohd.red.dao.UserMapper;
import com.qingguohd.red.model.User;


@Service
public class UserService {
	
	/**
	 * 取消了service实现
	 */
	
	private String keyList = "userList";
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	RedisUtils redis;

	public List<User> getUserList() {
		
		
		redis.set("userList", keyList);
		
		List<User> userList = userMapper.getUserList();
		redis.set(keyList, net.sf.json.JSONArray.fromObject(userList));
		return userList;
	}


}
