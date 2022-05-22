package com.fly.unicorn.motive.service;

import com.fly.unicorn.motive.entity.Wording;
import com.fly.unicorn.motive.repository.WordingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WordingServiceTest {

	@Autowired
	WordingRepository wordingRepository;

	@Test
	public void postTest() {
		System.out.println("SUCCESS");
		Wording wording = Wording.builder()
			.day(LocalDateTime.now())
			.description("test")
			.word("good")
			.writer("me")
			.build();

		wordingRepository.save(wording);
		System.out.println("finish");
	}

}
