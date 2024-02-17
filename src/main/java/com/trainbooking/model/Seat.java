package com.trainbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Seat {

    private int price;
    private String type;
    private int seatNo;
    private String section;
    private boolean booked;

    public void setBooked(Boolean booked){
        this.booked = booked;
    }
}
