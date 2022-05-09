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
public class Video {
	@JsonProperty(value = "VIDEO_ID")
	@Column(name = "video_id")
	@Min(value = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int videoId;

	@JsonProperty(value = "DAY")
	@Column(name = "day", columnDefinition = "TIMESTAMP")
	private LocalDateTime day;

	@JsonProperty(value = "TITLE")
	@Column(name = "title")
	@NotBlank
	private String title;

	@JsonProperty(value = "URL")
	@Column(name = "url")
	@NotBlank
	private String url;

	@JsonProperty(value = "RUNNING_TIME")
	@Column(name = "runningTime")
	private int runningTime;

	@JsonProperty(value = "COMMENT")
	@Column(name = "comment")
	private String comment;
}
