package com.fly.unicorn.motive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wording {
	@JsonProperty(value = "WORDING_ID")
	@Column(name = "wording_id")
	@Min(value = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int wordingId;

	@JsonProperty(value = "DAY")
	@Column(name = "day", columnDefinition = "TIMESTAMP")
	private LocalDateTime day;

	@JsonProperty(value = "WRITER")
	@Column(name = "writer")
	@NotBlank
	private String writer;

	@JsonProperty(value = "WORD")
	@Column(name = "word")
	@NotBlank
	private String word;

	@JsonProperty(value = "IMAGE")
	@Column(name = "image")
	private String image;

	@JsonProperty(value = "COMMENT")
	@Column(name = "comment")
	private String comment;
}
