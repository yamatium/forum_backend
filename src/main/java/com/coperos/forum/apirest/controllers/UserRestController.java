package com.coperos.forum.apirest.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coperos.forum.apirest.models.entity.User;
import com.coperos.forum.apirest.models.services.IUserService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/users")
	public List<User> index(){
		return userService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User show(@PathVariable Long id ) {
		return userService.findById(id);
	}
	
	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {	
		return userService.save(user);
	}
	
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> login(@RequestBody User user) {
		
		User existingUser = userService.findByEmail(user.getEmail());
		
		if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>("Login successful",HttpStatus.OK);
		} else {
			return new ResponseEntity<>("invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	
	
   	
	@PutMapping("/users/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public User update(@RequestBody User user, @PathVariable Long id) {
		User currentUser = userService.findById(id);
		
		currentUser.setUsername(user.getUsername());
		currentUser.setPassword(user.getPassword());
		currentUser.setEmail(user.getEmail());
		
		return userService.save(currentUser);
	}
	
	@PutMapping("/users/timeout/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User timeOut(@RequestBody User user, @PathVariable Long id) {
		User currentUser = userService.findById(id);
		
		currentUser.setStatus(user.getStatus());
		
		return userService.save(currentUser);
	}
	
	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}
	
	
	
	
	
	@GetMapping("/users/image/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (user.getImage() != null) {
            // Set the directory where the images are stored
            
            String fileName = user.getImage();
            
            Path imagePath = Paths.get(fileName);

            // Create a FileSystemResource for the image file
			FileSystemResource resource = new FileSystemResource(imagePath);

			// Return the image as a response with the appropriate content type
			return ResponseEntity.ok()
			        .contentType(MediaType.IMAGE_JPEG)
			        .body(resource);
        }

        // Return the user's information if no image is available
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
	
	
	
	 @PostMapping("users/{id}/upload")
		public ResponseEntity<String> uploadImage(@PathVariable("id") Long id,
		                                              @RequestParam("file") MultipartFile file) {
		        try {
		            // Get the file byte
		            @SuppressWarnings("unused")
					byte[] bytes = file.getBytes();

		            // Set the directory to save the file
		            String directory = "D:/CODEO/SPRING/workspace/forum-apirest/src/main/resources/static/uploads/user/"; 
		            String fileName = file.getOriginalFilename();

		            // Create the directory if it doesn't exist
		            File dir = new File(directory);
		            if (!dir.exists()) {
		                dir.mkdirs();
		            }

		            // Save the file
		            File uploadedFile = new File(directory + fileName);
		            OutputStream outputStream = new FileOutputStream(uploadedFile);
		            IOUtils.copy(file.getInputStream(), outputStream);
		            outputStream.close();

		            // Update the user's image field
		            User user = userService.findById(id);
		            user.setImage(directory + fileName);
		            userService.save(user);

		            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
		        } catch (IOException e) {
		            return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
	
	
	

}
