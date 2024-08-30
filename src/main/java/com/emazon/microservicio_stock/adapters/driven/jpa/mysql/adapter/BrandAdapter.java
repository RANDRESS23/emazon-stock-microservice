package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.BrandNotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.microservicio_stock.configuration.Constants;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new BrandAlreadyExistsException(Constants.BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE);
        }

        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public void deleteBrand(String name) {
        BrandEntity brandEntity = brandRepository.findByName(name)
                .orElseThrow(() -> new BrandNotFoundException(Constants.BRAND_NOT_FOUND));
        brandRepository.delete(brandEntity);
    }

    @Override
    public Optional<Brand> getBrand(String name) {
        return brandRepository.findByName(name)
                .map(brandEntityMapper::toDomainModel);
    }

    @Override
    public Page<Brand> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandEntityMapper::toDomainModel);
    }

    @Override
    public Optional<Brand> getBrandById(Long idBrand) {
        return brandRepository.findById(idBrand).map(brandEntityMapper::toDomainModel);
    }
}
