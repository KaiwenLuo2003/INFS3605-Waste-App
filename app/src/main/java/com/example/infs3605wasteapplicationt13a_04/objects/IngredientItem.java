package com.example.infs3605wasteapplicationt13a_04.objects;

public class IngredientItem {
    private String itemName;
    private String expiryDate;
    private int icon;
    private int quantity;


    public IngredientItem (String itemName, String expiryDate, int icon, int quantity){
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.icon = icon;
        this.quantity = quantity;
    }

    //getters/setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
