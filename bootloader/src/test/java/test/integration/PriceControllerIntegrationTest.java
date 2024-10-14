package test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.inditex.ecommerce.HexagonalEcommerceApplicationLauncher;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {HexagonalEcommerceApplicationLauncher.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/import-test_price.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = "/import-test_price_clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
class PriceControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
//	Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
	@Test
	void whenExistingPrice_shouldResponseOKTest1() throws Exception {
		
		Integer brandId = 1;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-14T10:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		
		verifyResult(
				resultActions, 
				BigDecimal.valueOf(35.50), 
				Instant.parse("2020-06-14T00:00:00Z"), 
				Instant.parse("2020-12-31T23:59:59Z"),
				brandId, 
				productId, 
				1L, 
				"EUR");
	}
	
//	Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
	@Test
	void whenExistingPrice_shouldResponseOKTest2() throws Exception {
		
		Integer brandId = 1;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-14T16:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		
		verifyResult(
				resultActions, 
				BigDecimal.valueOf(25.45), 
				Instant.parse("2020-06-14T15:00:00Z"), 
				Instant.parse("2020-06-14T18:30:00Z"), 
				brandId, 
				productId, 
				2L, 
				"EUR");
	}
	
//	Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
	@Test
	void whenExistingPrice_shouldResponseOKTest3() throws Exception {
		
		Integer brandId = 1;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-14T21:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		
		verifyResult(
				resultActions, 
				BigDecimal.valueOf(35.50), 
				Instant.parse("2020-06-14T00:00:00Z"), 
				Instant.parse("2020-12-31T23:59:59Z"), 
				brandId, 
				productId, 
				1L, 
				"EUR");
	}
	
//	Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
	@Test
	void whenExistingPrice_shouldResponseOKTest4() throws Exception {
		
		Integer brandId = 1;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-15T10:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		
		verifyResult(
				resultActions, 
				BigDecimal.valueOf(30.50), 
				Instant.parse("2020-06-15T00:00:00Z"), 
				Instant.parse("2020-06-15T11:00:00Z"), 
				brandId, 
				productId, 
				3L, 
				"EUR");
	}
	
//	Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
	@Test
	void whenExistingPrice_shouldResponseOKTest5() throws Exception {
		
		Integer brandId = 1;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-16T21:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		
		verifyResult(
				resultActions, 
				BigDecimal.valueOf(38.95), 
				Instant.parse("2020-06-15T16:00:00Z"), 
				Instant.parse("2020-12-31T23:59:59Z"), 
				brandId, 
				productId, 
				4L, 
				"EUR");
	}
	
	@Test
	void whenNoExistingPrice_shouldResponseNotFound() throws Exception {
		
		Integer brandId = 10;
		Integer productId = 35455;
		Instant date = Instant.parse("2020-06-16T21:00:00Z");
		
		ResultActions resultActions = callPriceGet(date, productId, brandId);
		resultActions.andExpect(status().isNotFound());
	}	
	
	private void verifyResult(ResultActions resultActions, BigDecimal price, Instant startDate, Instant endDate, 
			Integer brandId, Integer productId, Long priceList, String curr) throws Exception {
		
		MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
		JSONObject jsonObj = new JSONObject(mvcResult.getResponse().getContentAsString());
		
		assertEquals(price, BigDecimal.valueOf(jsonObj.getDouble("price")));
		assertEquals(startDate, Instant.parse(jsonObj.getString("startDate")));
		assertEquals(endDate, Instant.parse(jsonObj.getString("endDate")));
		assertEquals(brandId, jsonObj.getInt("brandId"));
		assertEquals(productId, jsonObj.getInt("productId"));
		assertEquals(priceList, jsonObj.getLong("priceList"));
		assertEquals(curr, jsonObj.getString("curr"));
	}
	
	private ResultActions callPriceGet(Instant date, Integer productId, Integer brandId) throws Exception {
		return mockMvc.perform(get("/prices?date=" + date + "&productId=" + productId + "&brandId=" + brandId));
	}
}
