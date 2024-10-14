package com.inditex.ecommerce.infrastructure.rest.controller;

import java.time.Instant;

import com.inditex.ecommerce.domain.model.PriceDTO;

public interface PriceController {
	PriceDTO findPrice(Instant date, Integer productId, Integer brandId);
}
