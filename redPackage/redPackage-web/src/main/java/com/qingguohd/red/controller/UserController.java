package com.qingguohd.red.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qingguohd.red.model.User;
import com.qingguohd.red.service.UserService;

/**
 * @author: YuGenHai
 * @name: UserController.java
 * @creation: 2018年5月31日 上午9:48:04
 * @notes:  测试
 */
@RestController
@RequestMapping(value = "/users" ,produces = "application/json;charset=utf-8")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public List<User> getUserList(@RequestParam(value = "name", required = false)String name,
			@RequestParam(value = "pasword", required = false) String pasword, @RequestParam(value = "adid", required = false) Integer adid) {
		User user = new User();
		user.setAdId(adid);
		user.setName(name);
		user.setPassword(pasword);
		int add = userService.insertUser(user);
		System.out.println(add);
		return userService.getUserList();
	}

	
	
}