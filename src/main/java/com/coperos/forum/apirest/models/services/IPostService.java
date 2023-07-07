package com.coperos.forum.apirest.models.services;

import java.util.List;

import com.coperos.forum.apirest.models.entity.Post;

public interface IPostService {
	
    public List<Post> findAll();
	
	public Post  findById (Long id);
	
	public Post save(Post post);
	
	public void delete(Long id);

}
