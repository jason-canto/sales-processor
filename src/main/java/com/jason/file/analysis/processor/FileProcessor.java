package com.jason.file.analysis.processor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jason.file.analysis.dto.Client;
import com.jason.file.analysis.dto.Result;
import com.jason.file.analysis.dto.Sale;
import com.jason.file.analysis.dto.Seller;
import com.jason.file.analysis.util.BuilderUtil;

@Component
public class FileProcessor implements Processor {

	private static final String FILE_SEPARATOR = "ç";

	private static final String SELLER_ID = "001";

	private static final String CLIENT_ID = "002";

	private static final String SALE_ID = "003";

	private static final Logger LOG = LoggerFactory.getLogger(FileProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		InputStream is = null; 
		String line = null;
		BufferedReader br = null;
		List<Seller> sellers = new ArrayList<>();
		List<Client> clients = new ArrayList<>();
		List<Sale> sales = new ArrayList<>();
		try {
			is = (InputStream) exchange.getIn().getBody();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				List<String> tokens = Arrays.asList(line.split(FILE_SEPARATOR));
				if (tokens != null && tokens.size() == 4 && StringUtils.isNotBlank(tokens.get(0))) {
					if (tokens.get(0).equals(SELLER_ID)) {
						sellers.add(BuilderUtil.sellerBuilder(tokens));
					} else if (tokens.get(0).equals(CLIENT_ID)) {
						clients.add(BuilderUtil.clientBuilder(tokens));
					} else if (tokens.get(0).equals(SALE_ID)) {
						sales.add(BuilderUtil.saleBuilder(tokens));
					}
				} else {
					LOG.error("Parse error - arguments dont match specification ", line);
				}
			}
			Result result =  Result.builder()
								.sellers(sellers)
								.clients(clients)
								.sales(sales).build();
			exchange.getIn().setBody(result);
		} catch(Exception e) {
			LOG.error("Error exception catched for file: ", e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(br);
		}
	}

}
