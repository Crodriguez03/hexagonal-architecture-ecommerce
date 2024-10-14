package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.inditex.ecommerce.domain.exception.NotFoundException;
import com.inditex.ecommerce.domain.model.PriceDTO;
import com.inditex.ecommerce.infrastructure.persistence.adapter.PriceServicePercistenceAdapter;
import com.inditex.ecommerce.infrastructure.persistence.entity.PriceEntity;
import com.inditex.ecommerce.infrastructure.persistence.mapper.PriceMapper;
import com.inditex.ecommerce.infrastructure.persistence.repository.PriceRepository;


@ExtendWith(MockitoExtension.class)
class PriceServicePercistenceAdapterUnitTest {

	@InjectMocks
	private PriceServicePercistenceAdapter priceServicePercistenceAdapter;
	
	@Mock
	private PriceRepository priceRepository;
	
	@Mock
	private PriceMapper priceMapper;

	@Test
	void findPrice_whenPriceFound_shouldReturnValidPriceDto() {
		
		Instant date = Instant.parse("2020-06-14T00:00:00Z");
		Instant startDate = date.minusSeconds(3600);
		Instant endDate = date.plusSeconds(3600);
		Integer productId = 35455;
		Integer brandId = 1;
		Long priceList = 1L;
		BigDecimal price = BigDecimal.valueOf(35.50);
		String curr = "EUR";
				
		PriceEntity priceEntity = givenPriceEntity(priceList, productId, brandId, startDate, endDate, price, curr);
		
		ArrayList<PriceEntity> prices = new ArrayList<>();
		prices.add(priceEntity);
		
		when(priceRepository.findPrice(date, productId, brandId, PageRequest.of(0,1))).thenReturn(prices);
		when(priceMapper.toDto(priceEntity)).thenReturn(givenPriceDto(priceList, productId, brandId, startDate, endDate, price, curr));
		
		PriceDTO priceDto = priceServicePercistenceAdapter.findPrice(date, productId, brandId);
		
		assertEquals(priceList, priceDto.getPriceList());
		assertEquals(brandId, priceDto.getBrandId());
		assertEquals(productId, priceDto.getProductId());
		assertEquals(startDate, priceDto.getStartDate());
		assertEquals(endDate, priceDto.getEndDate());
		assertEquals(price, priceDto.getPrice());
		assertEquals(curr, priceDto.getCurr());
	}
	
	@Test
	void findPrice_whenPriceNotFound_shouldThrowsNotFound() {
		
		Instant date = Instant.parse("2020-06-14T00:00:00Z");
		Integer productId = 35455;
		Integer brandId = 10;
		
		when(priceRepository.findPrice(any(), any(), any(), any())).thenReturn(new ArrayList<>());
		
		assertThrows(NotFoundException.class, () -> priceServicePercistenceAdapter.findPrice(date, productId, brandId));
	}
	
	private PriceEntity givenPriceEntity(Long priceList, Integer productId, Integer brandId, Instant startDate, Instant endDate, BigDecimal price, String curr) {
		return new PriceEntity(priceList, brandId, startDate, endDate, productId, null, price, curr);
	}
	
	private PriceDTO givenPriceDto(Long priceList, Integer productId, Integer brandId, Instant startDate, Instant endDate, BigDecimal price, String curr) {
		return new PriceDTO(priceList, productId, brandId, startDate, endDate, price, curr);
	}
}
