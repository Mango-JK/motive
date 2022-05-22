package com.fly.unicorn.motive.dto.response.wording;

import com.fly.unicorn.motive.entity.Wording;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WordingResponseDto {
	private long wordingId;
	private String day;
	private String writer;
	private String word;
	private String fileName;
	private String imagePath;
	private String description;

	public WordingResponseDto(Wording wording) {
		wordingId = wording.getWordingId();
		day = toStringDateTime(wording.getDay());
		writer = wording.getWriter();
		word = wording.getWord();
		fileName = wording.getFileName();
		description = wording.getDescription();
	}

	private String toStringDateTime(LocalDateTime localDateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Optional.ofNullable(localDateTime)
			.map(formatter::format)
			.orElse("");
	}
}
