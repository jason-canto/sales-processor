package com.jason.file.analysis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Client {

	private String cnpj;

	private String name;

	private String bussinessArea;

}
