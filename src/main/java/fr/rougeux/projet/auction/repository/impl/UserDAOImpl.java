package fr.rougeux.projet.auction.repository.impl;

import fr.rougeux.projet.auction.dto.SaleDTO;
import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.repository.UserDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO {

    public final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        String query = """
                SELECT u.user_id, u.last_name, u.first_name, u.email, u.user_img, u.phone, u.credit
                FROM USERS u
                WHERE u.email = :email
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(UserDTO.class));
    }
}
