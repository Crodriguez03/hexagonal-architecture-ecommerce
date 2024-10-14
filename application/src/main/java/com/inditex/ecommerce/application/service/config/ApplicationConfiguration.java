package com.inditex.ecommerce.application.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inditex.ecommerce.application.service.adapter.PriceServiceAdapter;
import com.inditex.ecommerce.application.service.api.PriceService;
import com.inditex.ecommerce.domain.spi.PricePersistencePort;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public PriceService getProductService(PricePersistencePort searchServicePort) {
        return new PriceServiceAdapter(searchServicePort);
    }
}
