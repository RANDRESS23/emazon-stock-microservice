package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.BrandValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;
    private final BrandValidation brandValidation;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort, BrandValidation brandValidation) {
        this.brandPersistencePort = brandPersistencePort;
        this.brandValidation = brandValidation;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        if(brandPersistencePort.getBrand(brand.getName()).isPresent()) {
            throw new AlreadyExistsFieldException(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE);
        }

        brandValidation.validateBrand(brand);
        return brandPersistencePort.saveBrand(brand);
    }

    @Override
    public void deleteBrand(String name) {
        Brand brand = brandPersistencePort.getBrand(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.BRAND_NOT_FOUND));
        brandPersistencePort.deleteBrand(brand.getName());
    }

    @Override
    public Brand getBrand(String name) {
        return brandPersistencePort.getBrand(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.BRAND_NOT_FOUND));
    }

    @Override
    public Page<Brand> getAllBrands(Integer page, Integer size, Boolean ascending) {
        Sort sort = Boolean.TRUE.equals(ascending) ? Sort.by(DomainConstants.FIELD_NAME).ascending() : Sort.by(DomainConstants.FIELD_NAME).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return brandPersistencePort.getAllBrands(pageable);
    }
}
