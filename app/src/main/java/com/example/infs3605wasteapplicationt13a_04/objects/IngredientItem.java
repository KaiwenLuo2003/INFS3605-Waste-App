package com.example.infs3605wasteapplicationt13a_04.objects;

public class IngredientItem {
    private int id;
    private String itemName;
    private String expiryDate;
    private int icon;
    private String quantity;


    public IngredientItem (int id, String itemName, String expiryDate, int icon, String quantity){
        this.id = id;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.icon = icon;
        this.quantity = quantity;
    }

    //getters/setters
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
