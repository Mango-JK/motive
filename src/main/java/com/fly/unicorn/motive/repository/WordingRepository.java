package com.fly.unicorn.motive.repository;

import com.fly.unicorn.motive.entity.Wording;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface WordingRepository extends JpaRepository<Wording, Long> {
	Stream<Wording> findAllByOrderByDayDesc();
}
