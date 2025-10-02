package fr.rougeux.projet.auction.service.impl;

import fr.rougeux.projet.auction.exception.BusinessCode;
import fr.rougeux.projet.auction.exception.BusinessException;
import fr.rougeux.projet.auction.service.SaleService;
import fr.rougeux.projet.auction.repository.SaleDAO;
import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.SaleDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleDAO saleDAO;

    public SaleServiceImpl(SaleDAO dao) {
        this.saleDAO = dao;
    }

    @Override
    public List<SaleDTO> readAllVente() {
        List<SaleDTO> saleLst = saleDAO.readAll();

        saleLst.forEach(sale -> {

            int currentBid = saleDAO.readMaxBid(sale.getSaleId());
            sale.setCurrentBid(currentBid);
        });
        return saleLst;
    }

    @Override
    public SaleDTO readSaleById(long saleId) {
        try {
            SaleDTO sale = saleDAO.read(saleId);

            List<BidDTO> bidLst = saleDAO.readAllBid(sale.getSaleId());
            sale.setBidLst(bidLst);

            return sale;
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_SALE_UNKNOWN);
        }
    }
}
