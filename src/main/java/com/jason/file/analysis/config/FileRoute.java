package com.jason.file.analysis.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.processor.FileProcessor;

@Component
public class FileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("{{route.from}}")
			.convertBodyTo(String.class)
			.split().tokenize("\n",1)
		       .choice()
		         .when(simple("${property.CamelSplitIndex} > 0"))
		           .to("direct:processLine")
		         .otherwise()
		           .to("direct:processHeader");
			.process(new FileProcessor())
			.to("{{route.to}}");
			
		from("direct:processLine")
			 .bean(processLineBean)
			 .to(B);

		from("direct:processHeader")
			 .bean(processHeaderBean)
			 .to();
	}

}
