package com.expense.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expense.dto.ChangePasswordRequest;
import com.expense.dto.ForgotPasswordRequest;
import com.expense.dto.LoginRequest;
import com.expense.dto.RegisterRequest;
import com.expense.entity.User;
import com.expense.exception.UserAlreadyExistsException;
import com.expense.repository.UserRepository;
import com.expense.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> register(
	        @Valid @RequestPart("data") RegisterRequest request,
	        @RequestPart(value = "image", required = false) MultipartFile image
	) throws IOException {

	    if (userRepository.existsByUsername(request.getUsername())) {
	        throw new UserAlreadyExistsException(
	            "User already exist: " + request.getUsername()
	        );
	    }

	    User user = new User();
	    user.setName(request.getName());
	    user.setUsername(request.getUsername());
	    user.setPassword(request.getPassword());

	    if (image != null && !image.isEmpty()) {
	        String profile = saveImage(image);
	        user.setProfileImage(profile);
	    }

	    userRepository.save(user);

	    return ResponseEntity.ok(
	        Map.of(
	            "message", "User registered successfully",
	            "status", 200
	        )
	    );
	}

	@PutMapping("/update-profile")
	public ResponseEntity<?> updateProfile(@RequestParam String username,
			@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<User> optionalUser = userRepository.findByUsername(currentUsername);

		if (optionalUser.isEmpty()) {
			throw new RuntimeException("Invalid user in token: " + currentUsername);
		}

		User user = optionalUser.get();

		// update username
		user.setUsername(username);


		// image upload
		if (image != null && !image.isEmpty()) {

			String profile = saveImage(image);

			// save image name in DB
			user.setProfileImage(profile);
		}

		User updatedUser = userRepository.save(user);

		Map<String, Object> response = new HashMap<>();

		response.put("username", updatedUser.getUsername());

		response.put("profileImage", updatedUser.getProfileImage());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {

		Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

		Map<String, Object> response = new HashMap<>();

		if (optionalUser.isEmpty()) {

			response.put("message", "Invalid username");

			return ResponseEntity.badRequest().body(response);
		}

		User user = optionalUser.get();

		if (!user.getPassword().equals(request.getPassword())) {

			response.put("message", "Invalid password");

			return ResponseEntity.badRequest().body(response);
		}

		String token = jwtUtil.generateToken(user.getUsername());

		response.put("token", token);
		response.put("username", user.getUsername());
		response.put("profileImage", user.getProfileImage());
		response.put("name", user.getName());

		return ResponseEntity.ok(response);
	}

	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {

		Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

		if (optionalUser.isEmpty()) {

			return ResponseEntity.badRequest().body("Username not found");
		}

		User user = optionalUser.get();

		user.setPassword(request.getNewPassword());

		userRepository.save(user);

		return ResponseEntity.ok("Password updated successfully");
	}
	
	
			@PutMapping("/change-password")
			public ResponseEntity<String> changePassword(
			        @RequestBody ChangePasswordRequest request) {

			    String username =
			            SecurityContextHolder
			            .getContext()
			            .getAuthentication()
			            .getName();

			    User user =
			            userRepository
			            .findByUsername(username)
			            .orElseThrow(() ->
			                    new RuntimeException(
			                            "User not found"));



			    // VERIFY CURRENT PASSWORD

			    if (!user.getPassword()
			            .equals(request.getCurrentPassword())) {

			        return ResponseEntity
			                .badRequest()
			                .body("Current password is incorrect");
			    }



			    // UPDATE PASSWORD

			    user.setPassword(
			            request.getNewPassword());

			    userRepository.save(user);

			    return ResponseEntity.ok(
			            "Password changed successfully");
			}
			
			private String saveImage(MultipartFile image) throws IOException {
				String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

				Path uploadPath = Paths.get("uploads");

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
				return fileName;
			}
			


}