package com.inditex.ecommerce.infrastructure.rest.controller;


import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.ecommerce.domain.model.PriceDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("prices")
public class PriceControllerImpl implements PriceController {
	
	@ApiResponse(responseCode = "200", description = "Precio encontrado")
	@ApiResponse(responseCode = "400", description = "Parámetros incorrectos en la llamada")
	@ApiResponse(responseCode = "404", description = "Precio no encontrado")
	@Operation(summary = "Consulta precio producto", description = "Consulta el precio de un producto perteneciente a una cadena")
	@Override
	@GetMapping
	public PriceDTO findPrice(
			@Schema(type = "string", example = "2020-06-14T00:00:00Z")
			@RequestParam(name = "date") Instant date, 
			@Schema(type = "integer", example = "35455")
			@RequestParam(name = "productId") Integer productId, 
			@Schema(type = "integer", example = "1")
			@RequestParam(name = "brandId") Integer brandId)  {
		return new PriceDTO(
				1L,
				productId,
				brandId,
				date.minusSeconds(3600),
				date.plusSeconds(3600),
				BigDecimal.valueOf(35.50),
				"EUR");
	}
}
