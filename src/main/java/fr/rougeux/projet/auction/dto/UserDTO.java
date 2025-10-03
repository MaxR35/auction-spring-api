package fr.rougeux.projet.auction.dto;

import java.util.List;

public class UserDTO {

    private long userId;
    private String lastName;
    private String firstName;
    private String email;
    private String userImg;
    private String phone;
    private int credit;

    private List<SaleDTO> sales;

    public List<SaleDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleDTO> saleList) {
        this.sales = saleList;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
