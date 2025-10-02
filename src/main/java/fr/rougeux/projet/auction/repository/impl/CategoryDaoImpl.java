package fr.rougeux.projet.auction.repository.impl;

import fr.rougeux.projet.auction.dto.CategoryDTO;
import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.entity.Category;
import fr.rougeux.projet.auction.repository.CategoryDao;
import fr.rougeux.projet.auction.repository.SaleDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    public final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CategoryDTO> readAll() {
        String query = """
                SELECT c.category_id, c.label, COUNT(i.category_id) as item_count FROM CATEGORIES c LEFT JOIN ITEMS i ON c.category_id = i.category_id
                group by c.category_id, c.label
                """;

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CategoryDTO.class));
    }
}
