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

import com.coperos.forum.apirest.models.entity.Game;
import com.coperos.forum.apirest.models.services.IGameService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class GameRestController {
	
	@Autowired
	private IGameService gameService;
	
	@GetMapping("/games")
	public List<Game> index(){
		return gameService.findAll();
	}
	
	@GetMapping("/games/{id}")
	public Game find(@PathVariable Long id) {
		return gameService.findById(id);
	}
	
	@PostMapping("/games")
	@ResponseStatus(HttpStatus.CREATED)
	public Game create(@RequestBody Game game) {
		return gameService.save(game);
	}
	
	@PutMapping("/games/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Game update(@RequestBody Game game, @PathVariable Long id) {
		Game currentGame = gameService.findById(id);
		
		currentGame.setName(game.getName());
		currentGame.setInfo(game.getInfo());
		currentGame.setDeveloper(game.getDeveloper());
		currentGame.setGenre(game.getGenre());
		currentGame.setPlatform(game.getPlatform());
		currentGame.setReleaseDate(game.getReleaseDate());
		
		return gameService.save(currentGame);
	}
	
	@DeleteMapping("/games/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		gameService.delete(id);
	}
	
	
	
	@GetMapping("/games/image/{id}")
    public ResponseEntity<?> getgameImageById(@PathVariable("id") Long id) {
        Game game = gameService.findById(id);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (game.getImage() != null) {
            // Set the directory where the images are stored
            
            String fileName = game.getImage();
            
            Path imagePath = Paths.get(fileName);

            // Create a FileSystemResource for the image file
			FileSystemResource resource = new FileSystemResource(imagePath);

			// Return the image as a response with the appropriate content type
			return ResponseEntity.ok()
			        .contentType(MediaType.IMAGE_JPEG)
			        .body(resource);
        }

        // Return the user's information if no image is available
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
	
	
	
	 @PostMapping("games/{id}/upload")
		public ResponseEntity<String> uploadImage(@PathVariable("id") Long id,
		                                              @RequestParam("file") MultipartFile file) {
		        try {
		            // Get the file byte
		            @SuppressWarnings("unused")
					byte[] bytes = file.getBytes();

		            // Set the directory to save the file
		            String directory = "D:/CODEO/SPRING/workspace/forum-apirest/src/main/resources/static/uploads/game/"; 
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
		            Game game = gameService.findById(id);
		            game.setImage(directory + fileName);
		            gameService.save(game);

		            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
		        } catch (IOException e) {
		            return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
	
	
	

}
