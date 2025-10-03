package fr.rougeux.projet.auction.repository;

import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.SaleDTO;

import java.util.List;

public interface SaleDAO {

    public List<SaleDTO> readAll();
    public SaleDTO read(long saleId);

    public List<BidDTO> readAllBid(long saleId);
    public Integer readMaxBid(long saleId);

    public List<SaleDTO> readAllByUserId(long userId);
}
