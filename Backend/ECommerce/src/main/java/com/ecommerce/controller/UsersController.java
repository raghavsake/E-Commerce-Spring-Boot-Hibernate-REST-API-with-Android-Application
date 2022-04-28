package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.model.Image;
import com.ecommerce.model.LoginApiResponse;
import com.ecommerce.model.RegisterApiResponse;
import com.ecommerce.service.FileStorageService;
import com.ecommerce.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UsersService userService;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@GetMapping(value="/login")
	public ResponseEntity<LoginApiResponse> login(@RequestParam("email")  String email,
			@RequestParam("password")  String password)
	{
		LoginApiResponse loginApiResponse=null;
		try {
			loginApiResponse = userService.login(email, password);
		if(loginApiResponse.getMessage().equals("Invalid Password"))
			return ResponseEntity.status(500).body(loginApiResponse);
		return ResponseEntity.status(200).body(loginApiResponse);
		}catch(Exception e) {
			return ResponseEntity.status(214).body(new LoginApiResponse(true,"Account not found"));
		}


	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<RegisterApiResponse> register(@RequestParam("name")String name,
			@RequestParam("gender")String gender, @RequestParam("age")int age, 
			@RequestParam("email")String email, @RequestParam("password")String password) {
	
		System.out.println("Hello");
		try
		{
			return ResponseEntity.status(200).body(userService.register(name,email,password,gender,age,""));
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body(new RegisterApiResponse(true, "Registration could not be done"));
			
		}
	}
	
	@DeleteMapping(value="/delete")
	public ResponseEntity<String> delete(@RequestParam("id")  int id)
	{
		try
		{
			userService.deleteUser(id);
			return ResponseEntity.status(200).body("Account is deleted");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Account could not be deleted");
			
		}
	}
	
	@PutMapping(value="/upload")
	public ResponseEntity<String> upload(@RequestParam("id")  int id,
			@RequestParam("image") MultipartFile file)
	{
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();
		try
		{
			userService.updateImage(id, fileDownloadUri);
			return ResponseEntity.status(200).body("Image updated");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Image could not be updated");
			
		}
	}
	
	@PutMapping(value="/info")
	public ResponseEntity<String> updatePassword(@RequestParam("id")  int id,
			@RequestParam("password")  String password)
	{
		try
		{
			userService.updatePassword(id,password);
			return ResponseEntity.status(200).body("Password is updated");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Password could not be updated");
			
		}

	}
	
	@GetMapping(value="/getImage")
	public ResponseEntity<Image> getImage(@RequestParam("id")int id)
	{
		try
		{
			Image i =userService.getImage(id);
			return ResponseEntity.status(200).body(i);
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body(new Image(true, "Could not get image", null));
			
		
		}
	}
	
	
	
}
