package com.fly.unicorn.motive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wording extends BaseTimeEntity {
	@JsonProperty(value = "WORDING_ID")
	@Column(name = "wording_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long wordingId;

	// Todo User

	@JsonProperty(value = "DAY")
	@Column(name = "day", columnDefinition = "TIMESTAMP")
	private LocalDateTime day;

	@JsonProperty(value = "WRITER")
	@Column(name = "writer", nullable = false)
	private String writer;

	@JsonProperty(value = "WORD")
	@Column(name = "word")
	@NotBlank
	private String word;

	@Column(name = "file_name")
	private String fileName;

	@ToString.Exclude
	@Column(name = "image_path")
	private String imagePath;

	@JsonProperty(value = "DESCRIPTION")
	@Column(name = "description")
	@Lob
	private String description;

	// Todo  Comment

	public void setImage(String fileName, String path) {
		this.fileName = fileName;
		this.imagePath = path;
	}

	public void update(Wording request) {
		this.day = request.getDay();
		this.writer = request.getWriter();
		this.word = request.getWord();
		this.description = request.getDescription();
	}
}
