package com.coperos.forum.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coperos.forum.apirest.models.dao.IRolesDao;
import com.coperos.forum.apirest.models.entity.Roles;

@Service
public class RolesServiceImpl implements IRoleService{
	
	@Autowired
	private IRolesDao rolesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Roles> findAll() {
		
		return (List<Roles>) rolesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Roles findById(Long id) {
		
		return rolesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Roles save(Roles roles) {
		
		return rolesDao.save(roles);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
        rolesDao.deleteById(id);
		
	}

}
