package com.jason.file.analysis.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Report;

@Component
public class Processor {

	@Bean
	Report myService() {
		return new Report();
	}
}
