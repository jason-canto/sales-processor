package com.jason.file.analysis.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Client;
import com.jason.file.analysis.dto.Item;
import com.jason.file.analysis.dto.Result;
import com.jason.file.analysis.dto.Sale;
import com.jason.file.analysis.dto.Seller;

@Component
public class FileProcessor implements Processor {

	private static final String FILE_SEPARATOR = "ç";

	private static final String ITEM_SEPARATOR = ",";

	private static final String ITEM_VALUE_SPLIT = "-";

	@Override
	public void process(Exchange exchange) throws Exception {

		FileReader fr = (FileReader) exchange.getIn().getBody();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			List<Client> clients = br.lines()
					.filter(l -> l.startsWith("002"))
					.map(line -> Arrays.asList(line.split(FILE_SEPARATOR)))
					.map(token -> {
						return Client.builder()
								.cnpj(token.get(1))
								.name(token.get(2))
								.bussinessArea(token.get(3))
								.build();
					}).collect(Collectors.toList());

			List<Sale> sales = br.lines()
					.filter(l -> l.startsWith("003"))
					.map(line -> Arrays.asList(line.split(FILE_SEPARATOR)))
					.map(token -> {
						List<Item> items = new ArrayList<>();
						double totalSale = 0;
						String values = token.get(2);
						if (StringUtils.isNotBlank(values)) {
							String [] arrayItems = values.split(ITEM_SEPARATOR);
							for (String value : arrayItems) {
								String [] itemValues = value.split(ITEM_VALUE_SPLIT);
								items.add(Item.builder()
									.id(itemValues[0])
									.quantity(Integer.parseInt(itemValues[1]))
									.price(Double.parseDouble(itemValues[2]))
									.build());
								totalSale += Double.parseDouble(itemValues[2]);
							}
						}
						return Sale.builder()
								.id(token.get(0))
								.items(items)
								.salesManName(token.get(2))
								.total(totalSale)
								.build();
					}).collect(Collectors.toList());

			List<Seller> sellers = br.lines()
					.filter(l -> l.startsWith("001"))
					.map(line -> Arrays.asList(line.split(FILE_SEPARATOR)))
					.map(token -> {
						return Seller.builder()
								.cpf(token.get(1))
								.name(token.get(2))
								.salary(Double.parseDouble(token.get(3)))
								.build();
					}).collect(Collectors.toList());

			Result parseResult = Result.builder()
									.seller(sellers)
									.clients(clients)
									.sales(sales)
									.build();
			exchange.getIn().setBody(parseResult);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/*public List<Seller> getSellerList(Exchange exchange) throws Exception {
		List<Seller> sellers = null;
		try {
			InputStream is = (InputStream) exchange.getIn().getBody();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			sellers = 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sellers;
	}

	public List<Seller> getClientList(Exchange exchange) throws Exception {
		List<Seller> sellers = null;
	}*/
}
