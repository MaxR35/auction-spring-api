package fr.rougeux.projet.auction.bo;

public class Categorie {

    private long categorieId;
    private String label;

    // Getters | Setters
    public long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(long categorieId) {
        this.categorieId = categorieId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
