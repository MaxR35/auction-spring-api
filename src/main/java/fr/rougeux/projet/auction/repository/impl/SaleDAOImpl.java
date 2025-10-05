package fr.rougeux.projet.auction.repository.impl;

import fr.rougeux.projet.auction.bo.Categorie;
import fr.rougeux.projet.auction.dto.*;
import fr.rougeux.projet.auction.dto.post.BidRequest;
import fr.rougeux.projet.auction.repository.SaleDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SaleDAOImpl implements SaleDAO {

    public final NamedParameterJdbcTemplate jdbcTemplate;

    public SaleDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SaleDTO> readAll() {
        String query = """
                SELECT s.sale_id, s.starting_date, s.ending_date, s.starting_price,
                       u.user_id, u.last_name, u.first_name, u.user_img,
                       i.item_id, i.item_name, i.item_img, i.item_desc,
                       c.category_id, c.label
                FROM SALES s
                LEFT OUTER JOIN USERS u ON s.seller_id = u.user_id
                LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                """;

        return jdbcTemplate.query(query, new SaleRowMapper(true));
    }
    @Override
    public List<SaleDTO> readAllByUserId(long id) {
        String query = """
                SELECT s.sale_id, s.starting_date, s.ending_date, s.starting_price, s.seller_id,
                       i.item_id, i.item_name, i.item_img, i.item_desc,
                       c.category_id, c.label
                FROM SALES s
                LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                WHERE s.seller_id = :id
                """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        return jdbcTemplate.query(query, paramSource, new SaleRowMapper(false));
    }


    @Override
    public SaleDTO read(long saleId) {
        String query = """
                SELECT s.sale_id, s.starting_date, s.ending_date, s.starting_price,
                        u.user_id, u.last_name, u.first_name, u.user_img,
                        i.item_id, i.item_name, i.item_img, i.item_desc,
                        c.category_id, c.label
                 FROM SALES s
                 LEFT OUTER JOIN USERS u ON s.seller_id = u.user_id
                 LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                 LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                 WHERE s.sale_id = :id
                """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", saleId);

        return jdbcTemplate.queryForObject(query, paramSource, new SaleRowMapper(true));
    }

    @Override
    public List<BidDTO> readAllBid(long saleId) {
        String query = """
                    SELECT b.bid_id, b.bid_amount, b.bid_time,
                           u.user_id, u.last_name, u.first_name, u.user_img
                    FROM BIDS b
                    LEFT OUTER JOIN USERS u ON u.user_id = b.user_id
                    WHERE b.sale_id = :id
                    ORDER BY b.bid_amount DESC
                """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", saleId);

        return jdbcTemplate.query(query, paramSource, new BidRowMapper());
    }

    @Override
    public Integer readMaxBid(long saleId) {
        String query = """
                SELECT TOP 1 b.bid_amount FROM BIDS b
                WHERE b.sale_id = :id
                ORDER BY b.bid_amount DESC
                """;

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", saleId);

        try {
            return jdbcTemplate.queryForObject(query, paramSource, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }


    private record SaleRowMapper(boolean includeSeller) implements RowMapper<SaleDTO> {

        @Override
            public SaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                SaleDTO sale = new SaleDTO();

                sale.setSaleId(rs.getLong("sale_id"));
                sale.setStartingDate(rs.getObject("starting_date", LocalDateTime.class));
                sale.setEndingDate(rs.getObject("ending_date", LocalDateTime.class));
                sale.setStartingPrice(rs.getInt("starting_price"));
                sale.setStatus();

                if (includeSeller) {
                    // Vendeur
                    UserDTO seller = new UserDTO();

                    seller.setUserId(rs.getLong("user_id"));
                    seller.setLastName(rs.getString("last_name"));
                    seller.setFirstName(rs.getString("first_name"));
                    seller.setUserImg(rs.getString("user_img"));

                    sale.setSeller(seller);
                }

                // Article Vendu
                ItemDTO item = new ItemDTO();

                item.setItemId(rs.getLong("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemImg(rs.getString("item_img"));
                item.setItemDesc(rs.getString("item_desc"));

                // Categorie Article
                CategoryDTO category = new CategoryDTO();

                category.setCategoryId(rs.getLong("category_id"));
                category.setLabel(rs.getString("label"));

                item.setCategory(category);
                sale.setItem(item);

                return sale;
            }
        }

    private static class BidRowMapper implements RowMapper<BidDTO> {
        @Override
        public BidDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BidDTO bid = new BidDTO();

            bid.setBidId(rs.getLong("bid_id"));
            bid.setBidTime(rs.getObject("bid_time", LocalDateTime.class));
            bid.setBidAmount(rs.getInt("bid_amount"));

            UserDTO user = new UserDTO();

            user.setUserId(rs.getLong("user_id"));
            user.setLastName(rs.getString("last_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setUserImg(rs.getString("user_img"));

            bid.setUser(user);

            return bid;
        }
    }
}
