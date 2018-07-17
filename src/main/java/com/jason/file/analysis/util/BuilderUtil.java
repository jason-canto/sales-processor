package com.jason.file.analysis.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jason.file.analysis.dto.Client;
import com.jason.file.analysis.dto.Item;
import com.jason.file.analysis.dto.Sale;
import com.jason.file.analysis.dto.Seller;

public class BuilderUtil {

	private static final String ITEM_SEPARATOR = ",";

	private static final String ITEM_VALUE_SPLIT = "-";

	public static Seller sellerBuilder (List<String> tokens) throws ParseException {
		return Seller.builder()
				.cpf(tokens.get(1))
				.name(tokens.get(2))
				.salary(Double.parseDouble(tokens.get(3)))
				.build();
	}

	public static Client clientBuilder (List<String> tokens) throws ParseException {
		return Client.builder()
				.cnpj(tokens.get(1))
				.name(tokens.get(2))
				.bussinessArea(tokens.get(3))
				.build();
	}

	public static Sale saleBuilder (List<String> tokens) throws ParseException {
		List<Item> items = new ArrayList<>();
		double totalSale = 0;
		String values = StringUtils.remove(StringUtils.remove(tokens.get(2), "["), "]");
		if (StringUtils.isNotBlank(values)) {
			List<String> itemList = Arrays.asList(values.split(ITEM_SEPARATOR));
			for (String value : itemList) {
				List<String> itemValues = Arrays.asList(value.split(ITEM_VALUE_SPLIT));
				items.add(Item.builder()
					.id(itemValues.get(0))
					.quantity(Integer.parseInt(itemValues.get(1)))
					.price(Double.parseDouble(itemValues.get(2)))
					.build());
				totalSale += Double.parseDouble(itemValues.get(2));
			}
		}
		return Sale.builder()
				.id(tokens.get(1))
				.items(items)
				.salesManName(tokens.get(3))
				.total(totalSale)
				.build();
	}

}
