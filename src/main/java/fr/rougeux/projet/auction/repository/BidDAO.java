package fr.rougeux.projet.auction.repository;

import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.post.BidRequest;

public interface BidDAO {

    public void newBid(BidRequest bidRequest);
    public int findUserBidBySale(long saleId,long userId);
}
