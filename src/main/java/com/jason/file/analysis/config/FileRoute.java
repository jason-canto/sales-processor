package com.jason.file.analysis.config;

import java.io.FileReader;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.processor.FileProcessor;
import com.jason.file.analysis.processor.ReportProcessor;

@Component
public class FileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("{{route.from}}")
			.convertBodyTo(FileReader.class)
			.process(new FileProcessor())
			.process(new ReportProcessor())
			.to("{{route.to}}");

	}

}
