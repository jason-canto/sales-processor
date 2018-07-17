package com.jason.file.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jason.file.analysis.dto.Client;
import com.jason.file.analysis.dto.Report;
import com.jason.file.analysis.dto.Result;
import com.jason.file.analysis.dto.Sale;
import com.jason.file.analysis.dto.Seller;
import com.jason.file.analysis.processor.FileProcessor;
import com.jason.file.analysis.processor.ReportProcessor;
import com.jason.file.analysis.util.BuilderUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesProcessorApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private FileProcessor fileProcessor;

	@Autowired
	private ReportProcessor reportProcessor;

	@EndpointInject(uri = "mock:result")
	private MockEndpoint resultEndpoint;

	@Produce(uri = "direct:start")
	private ProducerTemplate template;

	private static final String FILE_SEPARATOR = "ç";

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void clientBuilderTest() throws ParseException {
		String line = "002ç2345675434544345çJose da SilvaçRural";
		Client client = BuilderUtil.clientBuilder(getTokens(line));
		assertEquals(client.getCnpj(), "2345675434544345");
		assertEquals(client.getName(), "Jose da Silva");
		assertEquals(client.getBussinessArea(), "Rural");
	}

	@Test
	public void sellerBuilderTest() throws ParseException {
		String line = "001ç1234567891234çPedroç50000";
		Seller seller = BuilderUtil.sellerBuilder(getTokens(line));
		assertEquals(seller.getCpf(), "1234567891234");
		assertEquals(seller.getName(), "Pedro");
		assertEquals(seller.getSalary(), Double.valueOf("50000"));
	}

	@Test
	public void saleBuilderTest() throws ParseException {
		String line = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
		Sale sale = BuilderUtil.saleBuilder(getTokens(line));
		assertEquals(sale.getId(), "10");
		assertEquals(sale.getSalesManName(), "Pedro");
		assertEquals(sale.getTotal(), Double.valueOf("105.6"));
	}

	@Test
	public void testFileProcessor() throws Exception {
		CamelContext ctx = new DefaultCamelContext();
		Exchange exchange = new DefaultExchange(ctx);
		File file = getInputFile();
		exchange.getIn().setBody(FileUtils.openInputStream(file));
		fileProcessor.process(exchange);
		assertTrue(exchange.getIn().getBody() instanceof Result);
		reportProcessor.process(exchange);
		assertTrue(exchange.getIn().getBody() instanceof Report);
	}

	private List<String> getTokens(String line) {
		return Arrays.asList(line.split(FILE_SEPARATOR));
	}

	private File getInputFile() throws URISyntaxException, IOException {
		return FileUtils.toFile(getClass().getResource("/" + "teste.dat"));
	}

}
