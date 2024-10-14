package com.inditex.ecommerce.infrastructure.persistence.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inditex.ecommerce.infrastructure.persistence.entity.PriceEntity;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
	
	@Query(value = "select "
			+ "new com.inditex.ecommerce.infrastructure.persistence.entity.PriceEntity("
			+ "p.priceList, "
			+ "p.brandId, "
			+ "p.startDate, "
			+ "p.endDate, "
			+ "p.productId, "
			+ "null, "
			+ "p.price, "
			+ "p.curr) "
			+ "FROM prices p where "
			+ "p.brandId = ?3 AND "
			+ "p.productId = ?2 AND "
			+ "p.startDate <= ?1 "
			+ "AND p.endDate >= ?1 "
			+ "ORDER BY priority DESC")
	List<PriceEntity> findPrice(Instant date, Integer productId, Integer brandId, Pageable pageable);
}