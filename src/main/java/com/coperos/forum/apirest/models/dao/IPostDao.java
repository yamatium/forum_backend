package com.coperos.forum.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.coperos.forum.apirest.models.entity.Post;

public interface IPostDao extends CrudRepository<Post, Long>{

}
