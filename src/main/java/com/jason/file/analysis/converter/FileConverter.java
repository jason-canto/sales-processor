package com.jason.file.analysis.converter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Report;

@Component
@ConfigurationPropertiesBinding
public class FileConverter implements Converter<Report, String> {

	@Override
	public String convert(Report report) {
		StringBuilder sb = new StringBuilder();
		if (report != null) {
			sb.append("Quantidade de clientes no arquivo de entrada: ");
			sb.append(report.getClientQtd());
			sb.append("\n");
			sb.append("Quantidade de vendedores no arquivo de entrada: ");
			sb.append(report.getSellerQtd());
			sb.append("\n");
			sb.append("ID da venda mais cara: ");
			sb.append(report.getMostExpensiveSale());
			sb.append("\n");
			sb.append("Pior vendedor: ");
			sb.append(report.getWorstSeller());
			sb.append("\n");
			return sb.toString();
		}
		return "Empty report";
	}

}