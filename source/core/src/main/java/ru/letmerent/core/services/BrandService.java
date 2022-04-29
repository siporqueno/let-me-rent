package ru.letmerent.core.services;

import ru.letmerent.core.entity.Brand;

import java.util.Optional;

public interface BrandService {

    Optional<Brand> findByBrandName(String brandName);
    Brand createBrand(Brand brand);
}
