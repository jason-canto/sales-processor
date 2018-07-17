package com.jason.file.analysis.config;

import java.io.InputStream;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.processor.FileProcessor;
import com.jason.file.analysis.processor.ReportProcessor;

@Component
public class FileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("{{route.from}}")
			.filter()
			.simple("${file:ext} == 'dat'")
			.convertBodyTo(InputStream.class)
			.process(new FileProcessor())
			.process(new ReportProcessor())
			.convertBodyTo(String.class)
			.to("{{route.to}}?fileName=${file:onlyname.noext}.done.${file:ext}");

	}

}
