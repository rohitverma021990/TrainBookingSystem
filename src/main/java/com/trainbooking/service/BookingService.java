package com.trainbooking.service;

import com.trainbooking.exception.NotFoundException;
import com.trainbooking.model.Booking;
import com.trainbooking.model.Seat;
import com.trainbooking.model.User;
import com.trainbooking.storage.BookingStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {
    private BookingStore store;
    public BookingService(){
        this.store = new BookingStore();
    }

    public String createBooking(String from, String to, User user, List<Seat> seats, String trainNo, int bookingAmount){
        String id = UUID.randomUUID().toString();
        for(Seat seat : seats)
            seat.setBooked(true);
        Booking booking = new Booking(id, user, seats, trainNo, bookingAmount, from, to);
        this.store.insert(id, booking);
        return id;
    }

    public Optional<Booking> getBookingForUser(User user){
        List<Booking> allBooking = this.store.getAll();
        return allBooking.stream().filter(b -> b.getUser()==user).findFirst();
    }

    public Booking getBookingInfo(String bookingId){
        try{
            return (Booking) this.store.get(bookingId);
        }catch (NotFoundException e){
            System.out.println("Caught exception : "+ e.getMessage());
        }
        return null;
    }

    public boolean cancelBooking(User user){
        Optional<Booking> booking = getBookingForUser(user);
        if(booking.isPresent()){
            return booking.get().cancelBooking();
        }
        return false;
    }

    public List<String> getAllBookingForTrain(String trainNo){
        List<Booking> allBooking = this.store.getAll();
        List<Booking> filteredBooking = allBooking.stream().filter(a -> a.getTrainNo().equals(trainNo)).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for(Booking booking: filteredBooking)
            result.add(booking.toString());
        return result;
    }

    public boolean updateBooking(Booking booking, List<Seat> seats, String trainNo, int extraBookingAmount, int seatPrices) throws NotFoundException {
        for(Seat seat : booking.getSeats())
            seat.setBooked(false);
        for(Seat seat : seats)
            seat.setBooked(true);
        int remain = booking.getBookingAmount() - seatPrices;
        int bookingPrice = 0;
        if(remain>0){
            System.out.println("Refund will be initiate for amount : "+ remain);
            bookingPrice = seatPrices;
        }else{
            bookingPrice = booking.getBookingAmount() + extraBookingAmount;
        }
        booking.updateBooking(seats, bookingPrice);
        return true;
    }
}
