package com.enigma.isma.dao;

import java.util.List;

import com.enigma.isma.entity.User;


public interface UserDao {

	public String saveMember(User user);

	public List<User> getAllMember();

	public User getMemberById(String id);

	public User getMemberByName(String nameMember);
	public User login (String email, String password);


}
