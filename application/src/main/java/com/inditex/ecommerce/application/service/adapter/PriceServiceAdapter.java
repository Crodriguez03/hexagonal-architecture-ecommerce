package com.inditex.ecommerce.application.service.adapter;

import java.time.Instant;

import com.inditex.ecommerce.application.service.api.PriceService;
import com.inditex.ecommerce.domain.model.PriceDTO;
import com.inditex.ecommerce.domain.spi.PricePersistencePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PriceServiceAdapter implements PriceService {

    private final PricePersistencePort pricePersistencePort;

	@Override
	public PriceDTO findPrice(Instant date, Integer productId, Integer brandId) {		
		return pricePersistencePort.findPrice(date, productId, brandId);
	}
}
