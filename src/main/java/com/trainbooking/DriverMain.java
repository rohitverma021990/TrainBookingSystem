package com.trainbooking;

import com.trainbooking.api.BookingController;
import com.trainbooking.api.TrainController;
import com.trainbooking.api.UserController;
import com.trainbooking.enums.SeatType;
import com.trainbooking.service.BookingService;
import com.trainbooking.service.TrainService;
import com.trainbooking.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class DriverMain {
    public static void main(String[] args) {
        TrainService trainService = new TrainService();
        UserService userService = new UserService();
        BookingService bookingService = new BookingService();

        TrainController trainController = new TrainController(trainService);
        BookingController bookingController = new BookingController(bookingService, userService, trainService);
        UserController userController = new UserController(userService);

        String trainNum = trainController.createTrain("London", "France");
        trainController.addSeat(1, SeatType.ECONOMY,"A",trainNum);
        trainController.addSeat(2, SeatType.ECONOMY,"A",trainNum);
        trainController.addSeat(1, SeatType.ECONOMY,"B",trainNum);
        trainController.addSeat(2, SeatType.ECONOMY,"B",trainNum);
        trainController.addSeat(3, SeatType.BUSSINESS,"A",trainNum);
        trainController.addSeat(4, SeatType.BUSSINESS,"A",trainNum);
        trainController.addSeat(3, SeatType.BUSSINESS,"B",trainNum);
        trainController.addSeat(4, SeatType.BUSSINESS,"B",trainNum);

        try {
            String userEmail1 = userController.createUser("Rohit1", "Verma1", "rohitverma1@gmail.com");
            String userEmail2 = userController.createUser("Rohit2", "Verma2", "rohitverma2@gmail.com");
            String userEmail3 = userController.createUser("Rohit3", "Verma3", "rohitverma3@gmail.com");

            String bookingId = bookingController.createBooking("London", "France", userEmail1, 20, 1, SeatType.ECONOMY);
            System.out.println("bookingId ="+ bookingId);
            String bookingId2 = bookingController.createBooking("London", "France", userEmail2, 60, 2, SeatType.BUSSINESS);
            System.out.println("bookingId2 ="+ bookingId2);
            List<String> users = new ArrayList<>();
            users.add(userEmail1);
            users.add(userEmail2);
            System.out.println("Booking for users : "+ bookingController.getBookingsForUsers(users));
            System.out.println("Booking info for booking id: "+ bookingController.getBookingInfo(bookingId));
            System.out.println("Booking cancel for user : " +userEmail2+" is " +bookingController.cancelBooking(userEmail2));
            String bookingId3 = bookingController.createBooking("London", "France", userEmail3, 90, 3, SeatType.BUSSINESS);
            System.out.println("bookingId3 ="+bookingController.getBookingInfo(bookingId3));
            System.out.println("Booking id3 updated : "+ bookingController.updateBooking(bookingId3,2,SeatType.ECONOMY, 40 ));
            System.out.println("bookingId3 ="+bookingController.getBookingInfo(bookingId3));
        }catch (Exception e){
            System.out.println("Caught exception "+ e.getMessage());
        }

        /**
         *  OutPut ---
         * bookingId =8533cd6e-7f2c-4696-9fe1-1ce5dd101cc4
         * bookingId2 =fddc70d3-4f07-446a-ad17-577457541709
         * Booking for users : {rohitverma1@gmail.com={ BookingId : 8533cd6e-7f2c-4696-9fe1-1ce5dd101cc4 user : rohitverma1@gmail.com seats : [com.trainbooking.model.Seat@7ba4f24f] trainNum : c775b446-9d7c-459e-954e-1d7cbae44363 from :  to:  bookingAmount : 20 status : SUCCESS booking date : Sat Feb 17 04:13:13 IST 2024, rohitverma2@gmail.com={ BookingId : fddc70d3-4f07-446a-ad17-577457541709 user : rohitverma2@gmail.com seats : [com.trainbooking.model.Seat@1f17ae12, com.trainbooking.model.Seat@4d405ef7] trainNum : c775b446-9d7c-459e-954e-1d7cbae44363 from :  to:  bookingAmount : 60 status : SUCCESS booking date : Sat Feb 17 04:13:13 IST 2024}
         * Booking info for booking id: { BookingId : 8533cd6e-7f2c-4696-9fe1-1ce5dd101cc4 user : rohitverma1@gmail.com seats : [com.trainbooking.model.Seat@7ba4f24f] trainNum : c775b446-9d7c-459e-954e-1d7cbae44363 from :  to:  bookingAmount : 20 status : SUCCESS booking date : Sat Feb 17 04:13:13 IST 2024
         * Booking cancel for user : rohitverma2@gmail.com is true
         * bookingId3 ={ BookingId : 04eda9e7-b3c9-44d8-8789-10a8bec86df2 user : rohitverma3@gmail.com seats : [com.trainbooking.model.Seat@1f17ae12, com.trainbooking.model.Seat@4d405ef7, com.trainbooking.model.Seat@49c2faae] trainNum : c775b446-9d7c-459e-954e-1d7cbae44363 from :  to:  bookingAmount : 90 status : SUCCESS booking date : Sat Feb 17 04:13:13 IST 2024
         * Refund will be initiate for amount : 50
         * Booking id3 updated : true
         * bookingId3 ={ BookingId : 04eda9e7-b3c9-44d8-8789-10a8bec86df2 user : rohitverma3@gmail.com seats : [com.trainbooking.model.Seat@17f052a3, com.trainbooking.model.Seat@2e0fa5d3] trainNum : c775b446-9d7c-459e-954e-1d7cbae44363 from :  to:  bookingAmount : 40 status : SUCCESS booking date : Sat Feb 17 04:13:13 IST 2024
         */

    }
}
