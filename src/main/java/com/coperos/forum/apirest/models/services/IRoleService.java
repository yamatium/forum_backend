package com.coperos.forum.apirest.models.services;

import java.util.List;

import com.coperos.forum.apirest.models.entity.Roles;

public interface IRoleService {
	
    public List<Roles> findAll();
	
	public Roles  findById (Long id);
	
	public Roles save(Roles roles);
	
	public void delete(Long id);

}
