package com.jason.file.analysis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Seller {

	private String cpf;

	private String name;

	private Double salary;
}
