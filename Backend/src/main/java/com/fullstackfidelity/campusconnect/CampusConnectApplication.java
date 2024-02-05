package com.fullstackfidelity.campusconnect;

//import com.fullstackfidelity.campusconnect.service.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping("/image")

public class CampusConnectApplication {
//	@Autowired
//	private StorageService service;
//
//	@PostMapping
//	public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
//		String uploadImage = service.uploadImage(file);
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(uploadImage);
//	}
//
//	@GetMapping("/{fileName}")
//	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
//		byte[] imageData=service.downloadImage(fileName);
//		return ResponseEntity.status(HttpStatus.OK)
//				.contentType(MediaType.valueOf("image/png"))
//				.body(imageData);
//
//	}
//
//	@PostMapping("/fileSystem")
//	public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file) throws IOException {
//		String uploadImage = service.uploadImageToFileSystem(file);
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(uploadImage);
//	}
//
//	@GetMapping("/fileSystem/{fileName}")
//	public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
//		byte[] imageData=service.downloadImageFromFileSystem(fileName);
//		return ResponseEntity.status(HttpStatus.OK)
//				.contentType(MediaType.valueOf("image/png"))
//				.body(imageData);
//
//	}
	public static void main(String[] args) {
		SpringApplication.run(CampusConnectApplication.class, args);
	}

}
