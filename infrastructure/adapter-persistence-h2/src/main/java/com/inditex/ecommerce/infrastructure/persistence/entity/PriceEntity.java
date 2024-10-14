package com.inditex.ecommerce.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "prices")
@Table(name = "prices", indexes = { @Index( name = "brandId_productId", columnList = "brandId, productId") } )
public class PriceEntity {

	@Id
	private Long priceList;
	private Integer brandId;
	private Instant startDate;
	private Instant endDate;
	private Integer productId;
	private Integer priority;
	private BigDecimal price;
	@Column(length = 3)
	private String curr;
}
