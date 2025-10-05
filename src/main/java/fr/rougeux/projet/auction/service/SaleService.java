package fr.rougeux.projet.auction.service;

import fr.rougeux.projet.auction.dto.SaleDTO;
import fr.rougeux.projet.auction.dto.post.BidRequest;
import fr.rougeux.projet.auction.dto.post.BidResponse;

import java.util.List;

public interface SaleService {

    public List<SaleDTO> readAllVente();
    public SaleDTO readSaleById(long saleId);
    public BidResponse placeBid(BidRequest bidRequest);
}
