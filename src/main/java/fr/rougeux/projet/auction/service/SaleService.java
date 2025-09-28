package fr.rougeux.projet.auction.service;

import fr.rougeux.projet.auction.dto.SaleDTO;

import java.util.List;

public interface SaleService {

    public List<SaleDTO> readAllVente();
    public SaleDTO readSaleById(long saleId);
}
