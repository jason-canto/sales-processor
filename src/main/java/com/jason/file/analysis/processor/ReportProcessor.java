package com.jason.file.analysis.processor;

import java.util.Comparator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Report;
import com.jason.file.analysis.dto.Result;
import com.jason.file.analysis.dto.Sale;

@Component
public class ReportProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(ReportProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		if (exchange.getIn().getBody() != null && exchange.getIn().getBody() instanceof Result) {
			Result result = (Result) exchange.getIn().getBody();
			Sale saleMax = result.getSales().stream()
					.max(Comparator.comparing(Sale::getTotal)).orElse(null);
			Sale saleMin = result.getSales().stream()
					.min(Comparator.comparing(Sale::getTotal)).orElse(null);

			Report report = new Report();
			report.setClientQtd(result.getClients().size());
			report.setSellerQtd(result.getSellers().size());
			report.setMostExpensiveSale(saleMax != null ? saleMax.getId() : null);
			report.setWorstSeller(saleMin != null ? saleMin.getSalesManName() : null);

			exchange.getIn().setBody(report);
		} else {
			String message = "Error to create report - Report empty";
			LOG.error(message);
			exchange.getIn().setBody(message);
		}
	}

}
