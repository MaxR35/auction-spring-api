package fr.rougeux.projet.auction.service.impl;

import fr.rougeux.projet.auction.dto.CategoryDTO;
import fr.rougeux.projet.auction.repository.CategoryDao;
import fr.rougeux.projet.auction.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao dao) {
        this.categoryDao = dao;
    }

    @Override
    public List<CategoryDTO> readAllCategories() {
        return categoryDao.readAll();
    }
}
