package com.inditex.ecommerce.application.service.api;

import java.time.Instant;

import com.inditex.ecommerce.domain.model.PriceDTO;


public interface PriceService {

	PriceDTO findPrice(Instant date, Integer productId, Integer brandId);
}
