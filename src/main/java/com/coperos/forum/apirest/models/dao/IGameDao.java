package com.coperos.forum.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.coperos.forum.apirest.models.entity.Game;

public interface IGameDao extends CrudRepository<Game, Long>{

}
