package com.jason.file.analysis.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Result {

	List<Seller> sellers;

	List<Client> clients;

	List<Sale> sales;

}
