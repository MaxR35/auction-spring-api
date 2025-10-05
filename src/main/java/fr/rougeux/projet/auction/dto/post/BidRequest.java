package fr.rougeux.projet.auction.dto.post;

import fr.rougeux.projet.auction.dto.UserDTO;

import java.time.LocalDateTime;

public class BidRequest {

    private Long saleId;
    private Long userId;
    private int bidAmount;
    private LocalDateTime bidTime = LocalDateTime.now();

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }


    public long getSaleId() {
        return saleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }


}
