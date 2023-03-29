package com.cogent.controller;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.entity.User;
import com.cogent.exception.RecourseNotFoundException;
import com.cogent.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	
	
	//get all user
	@GetMapping
	public List<User>getAllUser(){
		return this.userRepository.findAll();
		
	}
	//get user byId
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value = "id")long userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(()-> new RecourseNotFoundException("user not fournd with id " + userId));
				
		
	}
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
		
	}
	//update user
	
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id")long userId) {
		User oldInfoUser = this.userRepository.findById(userId).orElseThrow(()->new RecourseNotFoundException("user not found with id: "+userId ));
		oldInfoUser.setFirstName(user.getFirstName());
		oldInfoUser.setLastName(user.getLastName());
		oldInfoUser.setEmail(user.getEmail());
		return this.userRepository.save(oldInfoUser); 
	}
	
	//delete user by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<User>deleteUser(@PathVariable("id")long userId){
		User oldInfoUser = this.userRepository.findById(userId)
				.orElseThrow(()->new RecourseNotFoundException("user not found with id: "+userId ));
		this.userRepository.delete(oldInfoUser);
		return ResponseEntity.ok().build();
		
		
	}

}
