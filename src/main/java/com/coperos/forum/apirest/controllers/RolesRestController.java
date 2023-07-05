package com.coperos.forum.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coperos.forum.apirest.models.entity.Roles;
import com.coperos.forum.apirest.models.services.IRoleService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class RolesRestController {
	
	@Autowired
	private IRoleService rolesService;
	
	@GetMapping("/roles")
	public List<Roles> index(){
		return rolesService.findAll();
	}
	
	@GetMapping("/roles/{id}")
	public Roles show(@PathVariable Long id) {
		return rolesService.findById(id);
	}
	
	@PostMapping("/roles")
	@ResponseStatus(HttpStatus.CREATED)
	public Roles create(@RequestBody Roles roles) {
		return rolesService.save(roles);
	}
	
	@PutMapping("/roles/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Roles update (@RequestBody Roles roles, @PathVariable Long id) {
		Roles currentRole = rolesService.findById(id);
		
		currentRole.setRole(roles.getRole());
		
		return rolesService.save(currentRole);
	}
	
	@DeleteMapping("/roles/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) { 
		rolesService.delete(id);
	}

}
