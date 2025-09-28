package fr.rougeux.projet.auction.dto;

import java.time.LocalDateTime;

public class BidDTO {

    private long bidId;
    private LocalDateTime bidTime;
    private int bidAmount;
    private UserDTO user;

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
