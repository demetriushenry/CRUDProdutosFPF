package br.com.fpf.testecrudprodutos.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.fpf.testecrudprodutos.Application;
import br.com.fpf.testecrudprodutos.facade.Facade;
import br.com.fpf.testecrudprodutos.model.Category;
import br.com.fpf.testecrudprodutos.model.Origin;
import br.com.fpf.testecrudprodutos.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ProductControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private int id = 1;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpConverter;
	
	private Product product;
	
	private List<Product> productList = new ArrayList<>();
	
	@Autowired
	private Facade facade;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null", mappingJackson2HttpConverter);
	}
	
	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		product = facade.findOneProduct(id);
		productList = (List<Product>) facade.findAllProduct();
	}
	
	@Test
	public void createProduct() throws Exception {
		String productJson = json(new Product("Produto Teste", null, new BigDecimal("0.99"), Origin.NATIONAL, Category.BOOK));
		mockMvc.perform(post("/product/create")
				.contentType(contentType)
				.content(productJson))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void readAllProducts() throws Exception {
		mockMvc.perform(get("/product/readall"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	public void readOneProduct() throws Exception {
		mockMvc.perform(get("/product/readone/" + id))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	public void updateProduct() throws Exception {
		int lastIndex = productList.size() - 1;
		Product updateProduct = productList.get(lastIndex);
		updateProduct.setDescription("Produto Teste Atualizado!");
		String productJson = json(updateProduct);
		mockMvc.perform(put("/product/update/" + lastIndex)
				.contentType(contentType)
				.content(productJson))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProduct() throws Exception {
		int lastIndex = productList.size() - 1;
		int idProduct = productList.get(lastIndex).getId();
		mockMvc.perform(delete("/product/delete/" + idProduct)
				.contentType(contentType))
				.andExpect(status().isNoContent());
	}
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
