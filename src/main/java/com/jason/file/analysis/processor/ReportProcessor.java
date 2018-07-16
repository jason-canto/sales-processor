package com.jason.file.analysis.processor;

import java.util.Comparator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.jason.file.analysis.dto.Report;
import com.jason.file.analysis.dto.Result;
import com.jason.file.analysis.dto.Sale;

public class ReportProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Result result = (Result) exchange.getIn().getBody();
		Sale saleMax = result.getSales().stream()
				.max(Comparator.comparing(Sale::getTotal)).get();
		Sale saleMin = result.getSales().stream()
				.min(Comparator.comparing(Sale::getTotal)).get();

		Report report = new Report();
		report.setClientQtd(result.getClients().size());
		report.setSellerQtd(result.getSeller().size());
		report.setMostExpensiveSale(saleMax.getId());
		report.setWorstSeller(saleMin.getSalesManName());
		
	}

	
}
