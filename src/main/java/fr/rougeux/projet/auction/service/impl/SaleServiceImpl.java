package fr.rougeux.projet.auction.service.impl;

import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.dto.post.BidRequest;
import fr.rougeux.projet.auction.dto.post.BidResponse;
import fr.rougeux.projet.auction.exception.BusinessCode;
import fr.rougeux.projet.auction.exception.BusinessException;
import fr.rougeux.projet.auction.repository.BidDAO;
import fr.rougeux.projet.auction.repository.UserDAO;
import fr.rougeux.projet.auction.service.SaleService;
import fr.rougeux.projet.auction.repository.SaleDAO;
import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.SaleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);
    private final SaleDAO saleDAO;
    private final BidDAO bidDAO;
    private final UserDAO userDAO;

    public SaleServiceImpl(SaleDAO saleDAO, BidDAO bidDAO, UserDAO userDAO) {
        this.saleDAO = saleDAO;
        this.bidDAO = bidDAO;
        this.userDAO = userDAO;
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

    @Override
    @Transactional
    public BidResponse placeBid(BidRequest bidRequest) {
        /*TODO: revoir logique metier sale */

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userDAO.getUserByEmail(auth.getName());

        int existingBid = bidDAO.findUserBidBySale(bidRequest.getSaleId(), user.getUserId());

        int debit = bidRequest.getBidAmount() - existingBid;
        log.info("Existing bid from DB: {}", existingBid);
        if(debit > user.getCredit()) {
            throw new BusinessException("insufficient credit");
        }
        user.setCredit(user.getCredit() - debit);
        bidRequest.setUserId(user.getUserId());

        userDAO.update(user);
        bidDAO.newBid(bidRequest);

        SaleDTO sale = readSaleById(bidRequest.getSaleId());

        return new BidResponse(sale, user);
    }
}
