package com.fly.unicorn.motive.service;

import com.fly.unicorn.motive.dto.response.ResultCode;
import com.fly.unicorn.motive.dto.response.wording.WordingResponseDto;
import com.fly.unicorn.motive.entity.Wording;
import com.fly.unicorn.motive.repository.WordingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WordingService {

	private final CommonService commonService;
	private final WordingRepository wordingRepository;

	@Transactional
	public ResponseEntity postWording(Wording wording, @RequestParam(required = false) MultipartFile image) {
		// Todo Save-Update 쿼리가 한 번만 발생하는지 확인 필요
		long wordingId = wordingRepository.save(wording).getWordingId();
		log.info("saved wording :: {}", wordingId);

		log.info("image.isEmpty:{}", image.isEmpty());
		if (image != null && image.isEmpty()) {
			File savedImage = null;
			log.info("savedImage:{}", savedImage);
			savedImage = commonService.saveWordingImageFile(image, wordingId);
			log.info("savedImage:{}", savedImage);
			wording.setImage(savedImage.getName(), savedImage.getAbsolutePath());
		}
		log.info("good:{}");
		return commonService.getCommonResponse(HttpStatus.CREATED.value(), ResultCode.WORDING_CREATED.getCode(), wording, ResultCode.WORDING_CREATED.getMessage());
	}

	@Transactional(readOnly = true)
	public ResponseEntity getWordings() {
		List<WordingResponseDto> wordings = wordingRepository.findAllByOrderByDayDesc()
			.map(WordingResponseDto::new)
			.collect(Collectors.toList());

		return commonService.getCommonResponse(HttpStatus.OK.value(), ResultCode.OK.getCode(),
			wordings, ResultCode.OK.getMessage());
	}

	@Transactional(readOnly = true)
	public ResponseEntity getWording(long wordingId) {
		WordingResponseDto wording = wordingRepository.findById(wordingId).map(WordingResponseDto::new).orElseGet(null);

		if (wording == null)
			return commonService.getErrorResponse(HttpStatus.NOT_FOUND.value(),
				ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage());

		return commonService.getCommonResponse(HttpStatus.OK.value(),
			ResultCode.OK.getCode(), wording, ResultCode.OK.getMessage());
	}

	@Transactional
	public ResponseEntity updateWording(long wordingId, Wording request, MultipartFile image) {
		Wording wording = wordingRepository.findById(wordingId).orElseGet(null);
		if (wording == null)
			return commonService.getErrorResponse(HttpStatus.NOT_FOUND.value(), ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage());

		if (!image.isEmpty()) {
			File savedImage = null;
			savedImage = commonService.saveWordingImageFile(image, wordingId);
			wording.setImage(savedImage.getName(), savedImage.getAbsolutePath());
		}

		// Todo Update Check, Not Setter
		wording.update(request);
		WordingResponseDto updatedWording = new WordingResponseDto(wording);

		return commonService.getCommonResponse(HttpStatus.OK.value(),
			ResultCode.OK.getCode(), updatedWording, ResultCode.OK.getMessage());
	}

	@Transactional
	public ResponseEntity deleteWording(long wordingId) {
		Wording wording = wordingRepository.findById(wordingId).orElseGet(null);
		if (wording == null)
			return commonService.getErrorResponse(HttpStatus.NOT_FOUND.value(), ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage());

		wordingRepository.deleteById(wordingId);

		return commonService.getCommonResponse(HttpStatus.OK.value(), ResultCode.OK.getCode(), new WordingResponseDto(wording), ResultCode.OK.getMessage());
	}
}
