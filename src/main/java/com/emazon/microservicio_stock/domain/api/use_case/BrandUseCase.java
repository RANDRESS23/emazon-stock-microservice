package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.exception.BrandNotFoundException;
import com.emazon.microservicio_stock.domain.exception.InvalidBrandNameException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        if(brandPersistencePort.getBrand(brand.getName()).isPresent()) {
            throw new InvalidBrandNameException(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE);
        }

        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public void deleteBrand(String name) {
        Brand brand = brandPersistencePort.getBrand(name)
                .orElseThrow(() -> new BrandNotFoundException(DomainConstants.BRAND_NOT_FOUND));
        brandPersistencePort.deleteBrand(brand.getName());
    }

    @Override
    public Brand getBrand(String name) {
        return brandPersistencePort.getBrand(name)
                .orElseThrow(() -> new BrandNotFoundException(DomainConstants.BRAND_NOT_FOUND));
    }
}
