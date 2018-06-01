package com.qingguohd.red.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.qingguohd.red.model.User;


@Mapper
public interface UserMapper {

	int deleteByPrimaryKey(Long id);

	@Transactional
	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	
	//
	@Transactional
	List<User> getUserList();
}
