package com.jason.file.analysis.processor;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Report;

@Component
public class FileProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String text = (String) exchange.getIn().getBody();
		

	}
}
