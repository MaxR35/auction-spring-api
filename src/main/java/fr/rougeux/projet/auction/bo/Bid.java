package fr.rougeux.projet.auction.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bid {

    private long bidId;
    private LocalDateTime bidTime;
    private int bidAmount;

    private Sale sale;
    private User user;

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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
