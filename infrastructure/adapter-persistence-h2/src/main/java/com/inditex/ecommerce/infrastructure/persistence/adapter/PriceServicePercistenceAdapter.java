package com.inditex.ecommerce.infrastructure.persistence.adapter;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.inditex.ecommerce.domain.exception.NotFoundException;
import com.inditex.ecommerce.domain.model.PriceDTO;
import com.inditex.ecommerce.domain.spi.PricePersistencePort;
import com.inditex.ecommerce.infrastructure.persistence.entity.PriceEntity;
import com.inditex.ecommerce.infrastructure.persistence.mapper.PriceMapper;
import com.inditex.ecommerce.infrastructure.persistence.repository.PriceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PriceServicePercistenceAdapter implements PricePersistencePort {
	
	private final PriceRepository priceRepository;
	private final PriceMapper priceMapper;
	
	@Override
	public PriceDTO findPrice(Instant date, Integer productId, Integer brandId) {
		
		List<PriceEntity> prices = priceRepository.findPrice(date, productId, brandId, PageRequest.of(0,1));
		
		PriceEntity price = prices.stream().findFirst().orElseThrow(() -> new NotFoundException("Price not found"));
		
		return priceMapper.toDto(price);
	}
}