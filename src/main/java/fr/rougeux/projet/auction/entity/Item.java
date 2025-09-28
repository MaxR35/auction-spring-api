package fr.rougeux.projet.auction.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ITEMS")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "item_name", nullable = false, length = 30)
    private String itemName;

    @Nationalized
    @Column(name = "item_desc", nullable = false, length = 300)
    private String itemDesc;

    @Nationalized
    @Column(name = "item_img", nullable = false)
    private String itemImg;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonManagedReference
    private Category category;

    @OneToMany
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Set<Sale> sales = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

}