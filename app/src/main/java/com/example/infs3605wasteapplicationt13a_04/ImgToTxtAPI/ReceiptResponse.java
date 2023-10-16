package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

public class ReceiptResponse {
    private String receipt;

    public ReceiptResponse(String receipt){
        this.receipt = receipt;
    }
    public String getReceipt(){
        return receipt;
    }

    public void setReceipt(String receipt){
        this.receipt = receipt;
    }


}
