package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Brand;
import ru.letmerent.core.repositories.BrandRepository;
import ru.letmerent.core.services.BrandService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;

    @Override
    public Optional<Brand> findByBrandName(String brandName) {
        return repository.findByBrandName(brandName);
    }

    @Override
    public Brand createBrand(Brand brand) {
        return repository.save(brand);
    }
}
