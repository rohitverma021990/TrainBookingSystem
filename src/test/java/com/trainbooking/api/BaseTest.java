package com.trainbooking.api;

import com.trainbooking.enums.SeatType;
import com.trainbooking.exception.BadRequestException;
import com.trainbooking.model.Train;
import com.trainbooking.model.User;
import com.trainbooking.service.BookingService;
import com.trainbooking.service.TrainService;
import com.trainbooking.service.UserService;

public class BaseTest {

    BookingController bookingController;
    TrainController trainController;
    UserController userController;
    protected void setUpController(){
        UserService userService = new UserService();
        TrainService trainService = new TrainService();
        BookingService bookingService = new BookingService();
        bookingController = new BookingController(bookingService, userService, trainService);
        trainController = new TrainController(trainService);
        userController = new UserController(userService);
    }

    protected User getTestUser(String fName, String lName, String emailId) throws BadRequestException {
        String userEmailId = userController.createUser(fName, lName, emailId);
        return userController.getUserObj(userEmailId);
    }

    protected Train getTrain(String source, String dest){
        String trainNum = trainController.createTrain(source, dest);
        trainController.addSeat(1, SeatType.ECONOMY,"A",trainNum);
        trainController.addSeat(2, SeatType.ECONOMY,"A",trainNum);
        trainController.addSeat(1, SeatType.ECONOMY,"B",trainNum);
        trainController.addSeat(2, SeatType.ECONOMY,"B",trainNum);
        trainController.addSeat(3, SeatType.BUSSINESS,"A",trainNum);
        trainController.addSeat(4, SeatType.BUSSINESS,"A",trainNum);
        trainController.addSeat(3, SeatType.BUSSINESS,"B",trainNum);
        trainController.addSeat(4, SeatType.BUSSINESS,"B",trainNum);
        return trainController.getTrainObj(trainNum);
    }
}
