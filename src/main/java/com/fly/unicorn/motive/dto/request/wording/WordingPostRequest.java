package com.fly.unicorn.motive.dto.request.wording;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fly.unicorn.motive.entity.Wording;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WordingPostRequest {
	@JsonProperty(value = "DAY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@NotNull
	private LocalDateTime day;

	@JsonProperty(value = "WRITER")
	private String writer;

	@JsonProperty(value = "WORD")
	@NotNull
	private String word;

	@JsonProperty(value = "DESCRIPTION")
	@Lob
	private String description;

	public Wording toEntity() {
		return Wording.builder()
			.day(day)
			.writer(writer)
			.word(word)
			.description(description)
			.build();
	}
}
