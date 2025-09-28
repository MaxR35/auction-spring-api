package fr.rougeux.projet.auction.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SaleDTO {

    private long saleId;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;
    private int startingPrice;
    private int salePrice;
    private String status;
    private int currentBid;

    private UserDTO seller;
    private ItemDTO item;
    private List<BidDTO> bidLst;


    public void setStatut() {
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = this.endingDate.isAfter(LocalDateTime.now()) ? "ONGOING" : "OVER";

    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public List<BidDTO> getBidLst() {
        return bidLst;
    }

    public void setBidLst(List<BidDTO> bidLst) {
        this.bidLst = bidLst;
    }
}
