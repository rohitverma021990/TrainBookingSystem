package com.trainbooking.model;

import com.trainbooking.enums.BookingStatus;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Getter
public class Booking {
    private final User user;
    private List<Seat> seats;
    private String status;
    private final String trainNo;
    private int bookingAmount;
    private final String bookingId;
    private final String from;
    private final String to;
    private final Date bookingDate;
    private Date modifyBookingDate;
    private Date cancelBookingDate;

    public Booking(@NonNull String id, @NonNull final User user, @NonNull final List<Seat> seats, @NonNull final String trainNo, final int bookingAmount,
                   @NonNull String from, @NonNull String to){
        this.user = user;
        this.seats = seats;
        this.trainNo = trainNo;
        this.bookingAmount = bookingAmount;
        this.status = BookingStatus.SUCCESS.name();
        this.from = from;
        this.to = to;
        this.bookingDate = new Date();
        this.modifyBookingDate = null;
        this.cancelBookingDate = null;
        this.bookingId = id;
    }

    public boolean isConfirmed(){
        return this.status.equals("SUCCESS");
    }

    public boolean isCanceled(){
        return this.status.equals("CANCEL");
    }

    public void updateBooking(List<Seat> seats, int bookingPrice){
        this.seats = seats;
        this.bookingAmount = bookingPrice;
        this.modifyBookingDate = new Date();
    }

    public boolean cancelBooking(){
        if(isCanceled()) {
            System.out.println("Booking already canceled");
            return true;
        }
        this.status = BookingStatus.CANCEL.name();
        this.cancelBookingDate = new Date();
        for(Seat seat : this.seats)
                seat.setBooked(false);
        return true;
    }

    @Override
    public String toString(){
        String result = "{ BookingId : "+ bookingId+" user : "+ user.getEmailId()+" seats : "+ seats.toString()
                + " trainNum : "+ trainNo+" from : "+from+" to: "+to +" bookingAmount : "+ bookingAmount+" status : "+ status
                + " booking date : "+ bookingDate;
        return result;
    }
}
