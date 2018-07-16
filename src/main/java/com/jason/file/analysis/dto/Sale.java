package com.jason.file.analysis.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Sale {

	private String id;

	private String salesManName;

	private List<Item> items;

	private Double total;
}
