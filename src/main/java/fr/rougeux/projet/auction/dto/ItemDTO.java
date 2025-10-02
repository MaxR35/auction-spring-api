package fr.rougeux.projet.auction.dto;

import fr.rougeux.projet.auction.dto.CategoryDTO;

public class ItemDTO {

    private long itemId;
    private String itemName;
    private String itemDesc;
    private String itemImg;
    private CategoryDTO category;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO categorie) {
        this.category = categorie;
    }
}
