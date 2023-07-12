package com.coperos.forum.apirest.models.services;

import java.util.List;

import com.coperos.forum.apirest.models.entity.Game;

public interface IGameService {
	
public List<Game> findAll();
	
	public Game  findById (Long id);
	
	public Game save(Game game);
	
	public void delete(Long id);

}
