package fr.rougeux.projet.auction.repository.impl;

import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.post.BidRequest;
import fr.rougeux.projet.auction.repository.BidDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BidDaoImpl implements BidDAO {

    public final NamedParameterJdbcTemplate jdbcTemplate;

    public BidDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void newBid(BidRequest bidRequest) {
        String query = """
                INSERT INTO BIDS(bid_time, bid_amount, sale_id, user_id)
                VALUES (:time, :amount, :saleId, :userId)
                """;

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("time", bidRequest.getBidTime());
        parameterSource.addValue("amount", bidRequest.getBidAmount());
        parameterSource.addValue("saleId", bidRequest.getSaleId());
        parameterSource.addValue("userId", bidRequest.getUserId());

        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public int findUserBidBySale(long userId, long saleId) {
        String query = """
                SELECT TOP 1 b.bid_amount FROM BIDS b
                WHERE b.user_id = :userId AND b.sale_id = :saleId
                ORDER BY b.bid_amount DESC
                """;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId", userId);
        parameterSource.addValue("saleId", saleId);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}
