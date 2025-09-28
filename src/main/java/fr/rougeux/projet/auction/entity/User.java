package fr.rougeux.projet.auction.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Nationalized
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Nationalized
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Nationalized
    @Column(name = "user_img")
    private String userImg;

    @Nationalized
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Nationalized
    @Column(name = "phone", length = 15)
    private String phone;

    @Nationalized
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @ColumnDefault("0")
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    @ColumnDefault("sysdatetime()")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Bid> bids = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "seller_id")
    private Set<Sale> sales = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

}