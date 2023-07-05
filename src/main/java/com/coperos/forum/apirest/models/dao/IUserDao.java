package com.coperos.forum.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.coperos.forum.apirest.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{

}
