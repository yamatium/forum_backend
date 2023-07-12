package com.coperos.forum.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coperos.forum.apirest.models.dao.IGameDao;
import com.coperos.forum.apirest.models.entity.Game;

@Service
public class GameServiceImpl implements IGameService{
	
	@Autowired
	IGameDao gameDao;

	@Override
	@Transactional(readOnly = true)
	public List<Game> findAll() {
		return (List<Game>) gameDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Game findById(Long id) {
		return gameDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Game save(Game game) {
		return gameDao.save(game);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		gameDao.deleteById(id);	
	}

}
