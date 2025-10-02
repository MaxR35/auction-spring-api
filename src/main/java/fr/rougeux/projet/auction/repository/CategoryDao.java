package fr.rougeux.projet.auction.repository;

import fr.rougeux.projet.auction.dto.CategoryDTO;

import java.util.List;

public interface CategoryDao {

    public List<CategoryDTO> readAll();
}
