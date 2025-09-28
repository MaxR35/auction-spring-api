package fr.rougeux.projet.auction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SALES")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonManagedReference
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(name = "starting_date", nullable = false)
    private Instant startingDate;

    @Column(name = "ending_date", nullable = false)
    private Instant endingDate;

    @Column(name = "starting_price")
    private Integer startingPrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    @OneToMany
    @JoinColumn(name = "sale_id")
    private Set<Bid> bids = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Instant getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Instant startingDate) {
        this.startingDate = startingDate;
    }

    public Instant getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Instant endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

}