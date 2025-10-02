package fr.rougeux.projet.auction.service;

import fr.rougeux.projet.auction.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> readAllCategories();
}
