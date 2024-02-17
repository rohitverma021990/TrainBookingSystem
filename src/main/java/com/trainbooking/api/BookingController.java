package com.trainbooking.api;

import com.trainbooking.enums.SeatType;
import com.trainbooking.exception.InsufficentSeatsException;
import com.trainbooking.exception.NotFoundException;
import com.trainbooking.model.Booking;
import com.trainbooking.model.Seat;
import com.trainbooking.model.Train;
import com.trainbooking.model.User;
import com.trainbooking.service.BookingService;
import com.trainbooking.service.TrainService;
import com.trainbooking.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;

@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;
    private UserService userService;
    private TrainService trainService;

    public String createBooking(@NonNull final String from, @NonNull final String to, @NonNull final String userEmailId,
                                int bookingAmount, int numberOfSeat, @NonNull final SeatType seatType)
            throws InsufficentSeatsException {
        Train train = trainService.getTrainInfo(from, to);
        boolean isRequiredSeatsAvailable = trainService.isSeatAvailable(seatType, train.getId(), numberOfSeat);
        if (!isRequiredSeatsAvailable) {
            System.out.println("Requested Seats Are Not available");
            throw new InsufficentSeatsException();
        }
        User user = userService.getUser(userEmailId);
        List<Seat> seats = trainService.getAvailableSeats(train.getId(), numberOfSeat, seatType);
        return bookingService.createBooking(from, to, user, seats, train.getId(), bookingAmount);
    }

    public boolean cancelBooking(@NonNull final String userEmailId) {
        User user = userService.getUser(userEmailId);
        return bookingService.cancelBooking(user);
    }

    public List<String> getAllBookingForTrain(@NonNull final String trainNum) {
        return bookingService.getAllBookingForTrain(trainNum);
    }

    public Map<String, String> getBookingsForUsers(List<String> users) {
        Map<String, String> result = new HashMap<>();
        for (String userEmailId : users) {
            User user = userService.getUser(userEmailId);
            Optional<Booking> booking = bookingService.getBookingForUser(user);
            if (booking.isPresent())
                result.put(userEmailId, booking.get().toString());
        }
        return result;
    }

    public String getBookingInfo(@NonNull final String bookingId) {
        Booking booking = bookingService.getBookingInfo(bookingId);
        if (booking == null)
            return "Booking Not found";
        return booking.toString();
    }

    public Boolean updateBooking(@NonNull final String bookingId, final int requiredSeat,
                                 @NonNull final SeatType seatType, final int amount) {
        Booking booking = bookingService.getBookingInfo(bookingId);
        Train train = trainService.getTrainInfo(booking.getTrainNo());
        boolean isRequiredSeatsAvailable = trainService.isSeatAvailable(seatType, train.getId(), requiredSeat);
        if (!isRequiredSeatsAvailable) {
            System.out.println("Requested Seats Are Not available");
            return false;
        }
        List<Seat> seats = trainService.getAvailableSeats(train.getId(), requiredSeat, seatType);

        try {
            return bookingService.updateBooking(booking, seats, train.getId(), amount, requiredSeat * seatType.getPrice());
        } catch (NotFoundException e) {
            System.out.println("Caught Exception : " + e.getMessage());
        }
        return false;
    }

}
