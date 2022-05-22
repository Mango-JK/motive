package com.fly.unicorn.motive.service;

import com.fly.unicorn.motive.dto.response.CommonResponse;
import com.fly.unicorn.motive.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonService {

	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@Value("${wording.image.path}")
	public String wordingImagePath;

	public File saveWordingImageFile(MultipartFile image, long wordingId) {
		File savedImageFile;
		String fileName;
		String fileExtension = FilenameUtils.getExtension(image.getOriginalFilename());
		fileName = wordingImagePath + wordingId + "_" + LocalDate.now() + "." + fileExtension;

		savedImageFile = new File(fileName);
		try (OutputStream os = new FileOutputStream(savedImageFile)) {
			os.write(image.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return savedImageFile;
	}

	public ResponseEntity getCommonResponse(int responseCode, int resultCode, Object data, String message) {
		return ResponseEntity.status(responseCode)
			.body(new CommonResponse(LocalDateTime.now().format(DATETIME_FORMAT),
				resultCode, data, message));
	}

	public ResponseEntity getErrorResponse(int responseCode, int resultCode, String message) {
		return ResponseEntity.status(responseCode)
			.body(new ErrorResponse(LocalDateTime.now().format(DATETIME_FORMAT),
				resultCode, message));
	}
}
