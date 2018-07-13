package com.jason.file.analysis.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

public class FileRoute extends RouteBuilder {
	
	@Override
    public void configure() throws Exception {
		from("{{route.from}}").to("{{route.to}}");
    }

}
