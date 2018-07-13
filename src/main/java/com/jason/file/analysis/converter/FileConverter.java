package com.jason.file.analysis.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class FileConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String source) {
		if (source == null) {
			return null;
		}
		return LocalDate.parse(source, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
	}

}