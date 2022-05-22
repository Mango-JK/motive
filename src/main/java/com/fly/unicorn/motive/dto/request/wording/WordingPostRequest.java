package com.fly.unicorn.motive.dto.request.wording;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fly.unicorn.motive.entity.Wording;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WordingPostRequest {
	@JsonProperty(value = "DAY")
	@NotNull
	private String day;

	@JsonProperty(value = "WRITER")
	private String writer;

	@JsonProperty(value = "WORD")
	@NotNull
	private String word;

	@JsonProperty(value = "DESCRIPTION")
	@Lob
	private String description;

	public Wording toEntity() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return Wording.builder()
			.day(LocalDate.parse(day, formatter))
			.writer(writer)
			.word(word)
			.description(description)
			.build();
	}
}
