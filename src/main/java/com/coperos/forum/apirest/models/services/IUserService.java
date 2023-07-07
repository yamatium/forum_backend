package com.coperos.forum.apirest.models.services;

import java.util.List;

import com.coperos.forum.apirest.models.entity.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User  findById (Long id);
	
	public User save(User user);
	
	public void delete(Long id);
	
	

}
