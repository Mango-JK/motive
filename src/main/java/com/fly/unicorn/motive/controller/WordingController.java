package com.fly.unicorn.motive.controller;

import com.fly.unicorn.motive.dto.request.wording.WordingPostRequest;
import com.fly.unicorn.motive.service.WordingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class WordingController {

	private final WordingService wordingService;

	@PostMapping(value = "/wording", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity postWording(@Valid @RequestPart(value = "request") WordingPostRequest request,
												@RequestPart(value = "image", required = false) MultipartFile image) {
		return wordingService.postWording(request.toEntity(), image);
	}

	@GetMapping(value = "/wordings", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getWordings() {
		return wordingService.getWordings();
	}

	@GetMapping(value = "/wording/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getWording(@Min(value = 1) @PathVariable("id") long id) {
		return wordingService.getWording(id);
	}

	@PutMapping(value = "/wording/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity updateWording(@RequestPart(value = "request") WordingPostRequest request,
										@RequestPart(value = "image", required = false) MultipartFile image,
										@PathVariable("id") long id) {
		return wordingService.updateWording(id, request.toEntity(), image);
	}

	@DeleteMapping(value = "/wording/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteWording(@Min(value = 1) @PathVariable("id") long id) {
		return wordingService.deleteWording(id);
	}
}
