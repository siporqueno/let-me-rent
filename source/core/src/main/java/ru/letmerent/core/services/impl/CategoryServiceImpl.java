package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.repositories.CategoryRepository;
import ru.letmerent.core.services.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public Category getCategoryById(Long id) {
        return repository.getById(id);
    }
}
