package com.fly.unicorn.motive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Video extends BaseTimeEntity {
	@JsonProperty(value = "VIDEO_ID")
	@Column(name = "video_id")
	@Min(value = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long videoId;

	// Todo User

	@JsonProperty(value = "DAY")
	@Column(name = "day", columnDefinition = "TIMESTAMP")
	private LocalDateTime day;

	@JsonProperty(value = "TITLE")
	@Column(name = "title")
	@NotBlank
	private String title;

	@NotBlank
	@JsonProperty(value = "URL")
	@Column(name = "url")
	@Lob
	private String url;

	@JsonProperty(value = "THUMBNAIL")
	@Column(name = "thumbnail")
	@Lob
	private MultipartFile thumbnail;

	@JsonProperty(value = "DESCRIPTION")
	@Column(name = "description")
	@Lob
	private String description;

	@JsonProperty(value = "RUNNING_TIME")
	@Column(name = "runningTime")
	private int runningTime;

	// Todo Comment
}
