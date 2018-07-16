package com.jason.file.analysis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Report {

	private Integer clientQtd;

	private Integer sellerQtd;

	private String mostExpensiveSale;

	private String worstSeller;

}
