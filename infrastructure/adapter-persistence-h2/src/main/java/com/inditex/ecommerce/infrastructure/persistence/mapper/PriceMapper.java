package com.inditex.ecommerce.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.inditex.ecommerce.domain.model.PriceDTO;
import com.inditex.ecommerce.infrastructure.persistence.entity.PriceEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceMapper {
	public PriceDTO toDto(PriceEntity price);
}
