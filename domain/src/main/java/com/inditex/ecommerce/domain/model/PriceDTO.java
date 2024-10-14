package com.inditex.ecommerce.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Data;


@Data
public final class PriceDTO {
	
	private final Long priceList;
	private final Integer productId;
	private final Integer brandId;
	private final Instant startDate;
	private final Instant endDate;
	private final BigDecimal price;
	private final String curr;
}
