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
import org.springframework.web.server.ResponseStatusException;

import com.coperos.forum.apirest.models.entity.Post;
import com.coperos.forum.apirest.models.entity.User;
import com.coperos.forum.apirest.models.services.IPostService;
import com.coperos.forum.apirest.models.services.IUserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class PostRestController {
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/posts")
	public List<Post> index(){
		return postService.findAll();
	}
	
	@GetMapping("/posts/{id}")
	public Post show(@PathVariable Long id) {
		return postService.findById(id);
	}
	
	
	@PostMapping("/users/{id}/posts")
	@ResponseStatus(HttpStatus.CREATED)
	public Post createPost(@PathVariable Long id, @RequestBody Post post) {
		User currentUser = userService.findById(id);
		
		if( currentUser == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		
		post.setUser(currentUser);
		return postService.save(post);
		
	}
	
	@PutMapping("/users/{userid}/posts/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Post update(@PathVariable("userid") Long userid, @PathVariable Long id ,@RequestBody Post post) {
		
		Post currentPost = postService.findById(id);
		User currentUser = userService.findById(userid);
		
		/*if(currentPost == null || !currentPost.getUser().equals(currentUser)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND");
		}*/
		
		if(currentPost == null ) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND");
		} if(!currentPost.getUser().equals(currentUser)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		
		
		currentPost.setTitle(post.getTitle());
		currentPost.setImage(post.getImage());
		currentPost.setDescription(post.getDescription());
		
		return postService.save(currentPost);
	}
	
	@DeleteMapping("/users/{userid}/posts/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("userid") Long userid, @PathVariable Long id ) {
		
		Post currentPost = postService.findById(id);
		User currentUser = userService.findById(userid);
		
		if(currentPost == null ) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND");
		} if(!currentPost.getUser().equals(currentUser)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
		}
		
		postService.delete(id);
		
	}
	

}
