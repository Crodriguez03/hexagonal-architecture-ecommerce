package com.inditex.ecommerce.domain.spi;

import java.time.Instant;

import com.inditex.ecommerce.domain.model.PriceDTO;


public interface PricePersistencePort {

	PriceDTO findPrice(Instant date, Integer productId, Integer brandId);
}
