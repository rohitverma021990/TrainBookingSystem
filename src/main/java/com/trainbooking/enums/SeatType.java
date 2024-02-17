package com.trainbooking.enums;

public enum SeatType {
    ECONOMY("ECONOMY",20),
    BUSSINESS("BUSSINESS", 30);

    private String type;
    private int price;
    SeatType(String type, int price){
        this.type= type;
        this.price = price;
    }
    public String getType(){
        return this.type;
    }

    public int getPrice(){
        return this.price;
    }
}
