package com.qingguohd.red.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/")
	public List<User> getUserList() {
		return userService.getUserList();
	}

}