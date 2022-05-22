package com.fly.unicorn.motive.dto.response.wording;

import com.fly.unicorn.motive.entity.Wording;
import lombok.*;

@Setter
@Getter
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
		day = wording.getDay().toString();
		writer = wording.getWriter();
		word = wording.getWord();
		fileName = wording.getFileName();
		description = wording.getDescription();
	}
}
