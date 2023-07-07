package com.coperos.forum.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coperos.forum.apirest.models.dao.IPostDao;
import com.coperos.forum.apirest.models.entity.Post;

@Service
public class PostServiceImpl implements IPostService{

	@Autowired
	private IPostDao postDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Post> findAll() {
		
		return (List<Post>) postDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Post findById(Long id) {
		
		return postDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Post save(Post post) {
		
		return postDao.save(post);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		postDao.deleteById(id);
		
	}

}
