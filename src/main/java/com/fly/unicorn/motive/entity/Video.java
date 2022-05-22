package com.fly.unicorn.motive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Video extends BaseTimeEntity {
	@JsonProperty(value = "VIDEO_ID")
	@Column(name = "video_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long videoId;

	// Todo User

	@JsonProperty(value = "DAY")
	@Column(name = "day", columnDefinition = "TIMESTAMP")
	private LocalDate day;

	@JsonProperty(value = "TITLE")
	@Column(name = "title")
	private String title;

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
